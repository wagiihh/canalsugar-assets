package com.example.canalsugar2.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.canalsugar2.Models.Admin;
import com.example.canalsugar2.Models.Asset;
import com.example.canalsugar2.Models.AssetStat;
import com.example.canalsugar2.Models.AssetType;
import com.example.canalsugar2.Models.AssignedAsset;
// import com.example.canalsugar.Models.AssignedLaptops;
import com.example.canalsugar2.Models.Department;
// import com.example.canalsugar.Models.Laptop;
import com.example.canalsugar2.Models.User;
import com.example.canalsugar2.Repositories.AdminRepository;
import com.example.canalsugar2.Repositories.AssetRepository;
import com.example.canalsugar2.Repositories.AssetTypeRepository;
import com.example.canalsugar2.Repositories.AssignedAssetRepository;
// import com.example.canalsugar.Repositories.AssignedLaptopsRepository;
import com.example.canalsugar2.Repositories.DepartmentRepository;
// import com.example.canalsugar.Repositories.LaptopRepository;
// import com.example.canalsugar.Repositories.UserRepository;
import com.example.canalsugar2.Repositories.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/assigned")
public class AssignedAssetController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssetTypeRepository assetTypeRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private AssignedAssetRepository assignedAssetsRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @GetMapping("/assign")
    public ModelAndView showAssignAssetForm(HttpSession session) {
        ModelAndView mav = new ModelAndView("assignasset");
    
        // Get all assets
        List<Asset> allAssets = assetRepository.findAll();
    
        // Get all assigned assets by mapping the assignment to the asset itself
        List<Asset> assignedAssets = assignedAssetsRepository.findAll().stream()
                .map(AssignedAsset::getAsset)
                .collect(Collectors.toList());
    
        // Filter available assets that are not already assigned
        List<Asset> availableAssets = allAssets.stream()
                .filter(asset -> !assignedAssets.contains(asset))
                .collect(Collectors.toList());
    
        // Collect unique asset types for filtering
        List<String> assetTypes = availableAssets.stream()
                .map(asset -> asset.getAssetType().getName())
                .distinct()
                .collect(Collectors.toList());
    
        // Add data to the model
        mav.addObject("users", userRepository.findAll());  // List of all users
        mav.addObject("assets", availableAssets);           // List of available assets
        mav.addObject("assetTypes", assetTypes);            // List of unique asset types
        mav.addObject("assignedAsset", new AssignedAsset()); // New AssignedAssets object for the form
    
        return mav;
    }
    
    @PostMapping("/assign")
    public RedirectView assignAsset(@ModelAttribute AssignedAsset assignedAsset, BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("/admin/home");
        }
    
        Asset asset = assignedAsset.getAsset();
        AssignedAsset existingAssignment = assignedAssetsRepository.findByAsset(asset);
    
        if (existingAssignment != null) {
            result.rejectValue("asset", "error.assignedAsset", "This asset is already assigned to another user.");
            return new RedirectView("/admin/home");
        }
    
        assignedAssetsRepository.save(assignedAsset);
    
        return new RedirectView("/asset/viewassets");
    }
    @GetMapping("/viewassigned")
    public ModelAndView showassigendForm() {
        ModelAndView mav = new ModelAndView("viewassigned");

        AssignedAsset assignedAsset=new AssignedAsset();
        List<AssetType>assetTypes=assetTypeRepository.findAll();
        List<AssignedAsset> allAssigned = assignedAssetsRepository.findAll();
        mav.addObject("allAssigned", allAssigned);
        mav.addObject("assetTypes", assetTypes);

        return mav;
    }
    @GetMapping("editassigned/{asid}")
    public ModelAndView editAssignedForm(@PathVariable Integer asid, HttpSession session) {
        ModelAndView mav = new ModelAndView("editassigned");
        AssignedAsset oldAssigned = this.assignedAssetsRepository.findByAsid(asid);
        
        if (oldAssigned != null && oldAssigned.getAsset() != null) {
            Asset asset = assetRepository.findByAssetid(oldAssigned.getAsset().getAssetid());
            
            if (asset != null) {
                oldAssigned.setAsset(asset);
            } else {
                mav.setViewName("errorPage"); // Example: redirecting to an error page.
                mav.addObject("message", "Asset not found.");
                return mav;
            }
        }
        
        List<User> users = userRepository.findAll();
        mav.addObject("oldAssigned", oldAssigned);
        mav.addObject("users", users);
        return mav;
    }
    
    
    
        
    @PostMapping("editassigned/{asid}")
    public RedirectView updateAssigned(@ModelAttribute("oldAssigned") AssignedAsset oldAssigned, @PathVariable Integer asid) {
        AssignedAsset existingAssigned = assignedAssetsRepository.findByAsid(asid);
        
        if (existingAssigned == null) {
            throw new IllegalArgumentException("Assigned Asset not found");
        }
    
        User user = userRepository.findById(oldAssigned.getUser().getUserID())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        existingAssigned.setUser(user);
        
        this.assignedAssetsRepository.save(existingAssigned);
        
        return new RedirectView("/assigned/viewassigned");
    }

    @GetMapping("deleteassigned/{asid}")
    @Transactional
    public RedirectView deleteAssigned(@PathVariable Integer asid) {
        AssignedAsset assigned=this.assignedAssetsRepository.findByAsid(asid);
        this.assignedAssetsRepository.delete(assigned);

        return new RedirectView("/assigned/viewassigned");
    }

    @GetMapping("/viewavailable")
    public ModelAndView viewAssetForm() {
        ModelAndView mav = new ModelAndView("viewavailable");
    
        // Fetch all assets
        List<Asset> allAssets = assetRepository.findAll();
    
        // Fetch all assigned assets
        List<Asset> assignedAssets = assignedAssetsRepository.findAll().stream()
            .map(AssignedAsset::getAsset)
            .collect(Collectors.toList());
    
        // Determine available assets
        List<Asset> availableAssets = allAssets.stream()
            .filter(asset -> !assignedAssets.contains(asset))
            .collect(Collectors.toList());
    
        // Fetch asset types for filtering
        List<AssetType> assetTypes = assetTypeRepository.findAll();
    
        // Add objects to the model
        mav.addObject("assetTypes", assetTypes);
        mav.addObject("availableAssets", availableAssets);
    
        return mav;
    }

@GetMapping("/assignasset/{assetid}")
public ModelAndView showAssignAssetForm(@PathVariable Integer assetid) {
    ModelAndView mav = new ModelAndView("assignspecificasset");

    Asset asset = assetRepository.findByAssetid(assetid);
    if (asset == null) {
        // Handle the case where the asset is not found
        mav.setViewName("errorPage");
        mav.addObject("message", "Asset not found.");
        return mav;
    }

    AssignedAsset newAssignment = new AssignedAsset();
    newAssignment.setAsset(asset);

    List<User> users = userRepository.findAll();

    mav.addObject("newAssignment", newAssignment);
    mav.addObject("users", users);
    mav.addObject("asset", asset);

    return mav;
}


    @PostMapping("/assignasset/{assetid}")
    public RedirectView assignAsset(@ModelAttribute("newAssignment") AssignedAsset newAssignment,
                                    @RequestParam("userId") Integer userId,
                                    @PathVariable Integer assetid) {
        User user = userRepository.findByUserID(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Asset asset = assetRepository.findByAssetid(assetid);
        if (asset == null) {
            throw new IllegalArgumentException("Asset not found");
        }

        newAssignment.setUser(user);
        newAssignment.setAsset(asset);

        assignedAssetsRepository.save(newAssignment);

        eventPublisher.publishEvent(new AssetAssignedEvent(this, assetid, userId));


        return new RedirectView("/assigned/viewassigned");
    }

   @Async
    public void sendStockWarning() {
        if (mailSender == null) {
            System.out.println("MailSender is not configured");
            return;
        }
    
        try {
            List<AssetStat> assetStatisticsList = getAssetStatistics();
            
            StringBuilder emailMessage = new StringBuilder();
            emailMessage.append("PLEASE CHECK THE STOCK FOR THE FOLLOWING ASSET TYPES AS THEY ARE ALMOST EMPTY:\n\n");
    
            for (AssetStat stats : assetStatisticsList) {
                emailMessage.append(String.format("Asset Type: %s, Available Assets: %d\n", 
                    stats.getAssetType().getName(), stats.getAvailableAssets()));
            }
    
            List<Admin> allAdmins = this.adminRepository.findAll();
    
            for (Admin admin : allAdmins) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                
                helper.setFrom("tabibii.application@gmail.com");
                helper.setTo(admin.getEmail());
                helper.setSubject("STOCK WARNING!!!!!");
                helper.setText(String.format("Dear %s, \n\n%s\n\n Greetings from the Canal Sugar Asset Management Website.", 
                    admin.getFirstname(), emailMessage.toString()));
    
                mailSender.send(message);
                System.out.println("Mail sent to admin: " + admin.getEmail());
            }
    
            System.out.println("All emails sent successfully.");
    
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error while sending mail.");
        }
    }
    


    private List<AssetStat> getAssetStatistics() {
        List<AssetType> assetTypes = assetTypeRepository.findAll();
        List<AssetStat> assetStatisticsList = new ArrayList<>();
    
        for (AssetType assetType : assetTypes) {
            long totalAssets = assetRepository.countByAssetType(assetType);
            long usedAssets = assignedAssetsRepository.countByAsset_AssetType(assetType);
            long availableAssets = totalAssets - usedAssets;
    
            if (availableAssets < 4) {
                AssetStat stats = new AssetStat(assetType, totalAssets, usedAssets, availableAssets);
                assetStatisticsList.add(stats);
            }
        }
        
        return assetStatisticsList;
    }
}
    

