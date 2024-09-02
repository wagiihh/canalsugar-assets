package com.example.canalsugar2.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import com.example.canalsugar2.Models.AssetType;
// import com.example.canalsugar.Models.AssignedLaptops;
import com.example.canalsugar2.Models.Department;
// import com.example.canalsugar.Models.Laptop;
import com.example.canalsugar2.Models.User;
import com.example.canalsugar2.Repositories.AdminRepository;
import com.example.canalsugar2.Repositories.AssetRepository;
import com.example.canalsugar2.Repositories.AssetTypeRepository;
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
@RequestMapping("/asset")
public class AssetsController {
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

    
    @GetMapping("/addAssetType")
    public ModelAndView showAssetTypeForm() {
        ModelAndView mav = new ModelAndView("addAssetType");
        Admin newAdmin=new Admin();
        AssetType newAssetType=new AssetType();
        mav.addObject("newAssetType", newAssetType);
        return mav;
    }
    @PostMapping("addAssetType")
    public ModelAndView processSignupForm(@Valid @ModelAttribute("newAssetType") AssetType newAssetType, BindingResult result) {
        ModelAndView SignupModel = new ModelAndView("addAssetType.html");
        ModelAndView refresh = new ModelAndView("CSHOME.html");

        AssetType existingAssetType=assetTypeRepository.findByName(newAssetType.getName());
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/admin/addAssetType");
        }
        if (existingAssetType != null) {
            return SignupModel;
        } else {
            AssetType assetType=newAssetType.getAssetType();
            this.assetTypeRepository.save(assetType);
            return new ModelAndView("redirect:/admin/home");

        }
    }

    @GetMapping("/addAsset")
    public ModelAndView showAssetForm() {
        ModelAndView mav = new ModelAndView("addAsset");
        Asset newAsset = new Asset();
        List<AssetType> assetTypes = assetTypeRepository.findAll();
        mav.addObject("assetTypes", assetTypes);
        mav.addObject("newAsset", newAsset);
        return mav;
    }
    
    @PostMapping("addAsset")
    public ModelAndView processAssetForm(@Valid @ModelAttribute("newAsset") Asset newAsset, BindingResult result) {
        ModelAndView SignupModel = new ModelAndView("addAsset");
        ModelAndView refresh = new ModelAndView("CSHOME.html");

        Asset existingAsset=assetRepository.findByAssetserial(newAsset.getAssetserial());

        if (existingAsset != null) {
            return SignupModel;
        } else {
            Asset asset=newAsset.getAsset();
            this.assetRepository.save(asset);
            return new ModelAndView("redirect:/admin/viewassets");

        }
    }

    @GetMapping("/viewassets")
    public ModelAndView viewAssetForm() {
        ModelAndView mav = new ModelAndView("viewassets");
        List<Asset> assets = assetRepository.findAll();
        List<AssetType>assetTypes=assetTypeRepository.findAll();
        mav.addObject("assetTypes", assetTypes);
        mav.addObject("assets", assets);
        return mav;
    }
    @GetMapping("editasset/{assetid}")
    public ModelAndView editAppointmentForm(@PathVariable Integer assetid, HttpSession session) {
        ModelAndView mav = new ModelAndView("editasset");
        Asset oldasset=this.assetRepository.findByAssetid(assetid);
        System.out.println("-------------------------------------the user sent in the edit form :" + assetid);
        mav.addObject("oldasset", oldasset);
        List<AssetType>assetTypes=assetTypeRepository.findAll();
        mav.addObject("assetTypes", assetTypes);
        return mav;
    }

    @PostMapping("editasset/{assetid}")
    public RedirectView updateAppointment(@ModelAttribute("oldasset") Asset oldasset, @PathVariable Integer assetid) {
        oldasset.setAssetid(assetid);
        this.assetRepository.save(oldasset);
        return new RedirectView("/asset/viewassets");
    }

    @GetMapping("/viewassettypes")
    public ModelAndView viewAssetTypeForm() {
        ModelAndView mav = new ModelAndView("viewassettypes");
        List<AssetType>assetTypes=assetTypeRepository.findAll();
        mav.addObject("assetTypes", assetTypes);
        return mav;
    }
}
