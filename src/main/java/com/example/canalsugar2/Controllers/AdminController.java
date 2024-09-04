package com.example.canalsugar2.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.hibernate.mapping.Map;
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
import com.example.canalsugar2.Models.AssetStat;
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
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
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
    private AssignedAssetRepository assignedAssetRepository;

    @GetMapping("/home")
    public ModelAndView Home(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        String firstname = (String) session.getAttribute("Firstname");
        System.out.println("Firstname from session: " + firstname);
        ModelAndView mav = new ModelAndView("CSHome");
        mav.addObject("FNAME", firstname);
        return mav;
    }

    @GetMapping("/settings")
    public ModelAndView settings(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        String firstname = (String) session.getAttribute("Firstname");

        ModelAndView mav = new ModelAndView("AccountSettings");
        mav.addObject("email", (String) session.getAttribute("email"));
        mav.addObject("FNAME", firstname);
        return mav;
    }

    @GetMapping("Profile")
    public ModelAndView getProfile(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("Profile");

        mav.addObject("email", (String) session.getAttribute("email"));
        mav.addObject("firstname", (String) session.getAttribute("Firstname"));
        mav.addObject("lastname", (String) session.getAttribute("Lastname"));
        mav.addObject("number", (String) session.getAttribute("number"));
        return mav;
    }

    @GetMapping("editprofile")
    public ModelAndView getEditProfile(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("EditProfile");

        mav.addObject("email", (String) session.getAttribute("email"));
        mav.addObject("firstname", (String) session.getAttribute("Firstname"));
        mav.addObject("lastname", (String) session.getAttribute("Lastname"));
        mav.addObject("number", (String) session.getAttribute("number"));
        mav.addObject("adminID", (Integer) session.getAttribute("adminID"));

        return mav;
    }

    @PostMapping("/editprofile")
    public RedirectView editprocess(HttpSession session,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("number") String number) {
                
                
        Admin adminedit = this.adminRepository.findByEmail((String) session.getAttribute("email"));
        if (adminedit != null) {
            session.setAttribute("Firstname", firstname);
            session.setAttribute("Lastname", lastname);
            session.setAttribute("number", number);

            adminedit.setFirstname(firstname);
            adminedit.setLastname(lastname);
            adminedit.setNumber(number);
            this.adminRepository.save(adminedit);

            return new RedirectView("/admin/Profile");
        }
        return new RedirectView("/admin/editprofile?error=incorrectForm");

    }

    @GetMapping("/deleteAccount")
    public RedirectView deleteAccount(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
            return new RedirectView("/error");
        }
        Admin adminDelete = this.adminRepository.findByEmail((String) session.getAttribute("email"));
        if (adminDelete != null) {
            this.adminRepository.delete(adminDelete);
            session.invalidate();
            return new RedirectView("/");
        }
        return new RedirectView("/admin/Profile");
    }

    @GetMapping("/adduser")
    public ModelAndView showSignupForm(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("signup.html");
        User newUser = new User();
        mav.addObject("newUser", newUser);
        Department department = new Department();
        List<Department> departments = departmentRepository.findAll();
        mav.addObject("departments", departments);
        return mav;
    }

    @PostMapping("adduser")
    public ModelAndView processSignupForm(@Valid @ModelAttribute("newUser") User newUser, BindingResult result) {
        ModelAndView SignupModel = new ModelAndView("adduser.html");
        ModelAndView refresh = new ModelAndView("CSHOME.html");

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("signup.html");
            mav.addObject("newUser", newUser);
            Department department = new Department();
            List<Department> departments = departmentRepository.findAll();
            mav.addObject("departments", departments);
            return mav;
        }
    
        User existingUser = userRepository.findByEmail(newUser.getEmail());

        if (existingUser != null) {
            return SignupModel;
        } else {
            User user = newUser.getUser();
            this.userRepository.save(user);
            return new ModelAndView("redirect:/admin/viewUsers");

        }
    }

    @GetMapping("/viewUsers")
    public ModelAndView showAddUserForm(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("viewUsers.html");

        User newUser = new User();
        List<User> allUsers = userRepository.findAll();
        List<Department>allDepartments=departmentRepository.findAll();
        mav.addObject("allUsers", allUsers);
        mav.addObject("departments", allDepartments);

        return mav;
    }

    @GetMapping("edit/{userID}")
    public ModelAndView editAppointmentForm(@PathVariable Integer userID, HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("editUser.html");
        User oldUser = this.userRepository.findByUserID(userID);
        System.out.println("-------------------------------------the user sent in the edit form :" + oldUser);
        mav.addObject("oldUser", oldUser);
        List<Department> departments = departmentRepository.findAll();
        mav.addObject("departments", departments);
        return mav;
    }

    @PostMapping("edit/{userID}")
    public RedirectView updateAppointment(@Valid@ModelAttribute("oldUser") User oldUser, @PathVariable Integer userID,BindingResult result) {
        oldUser.setUserID(userID);
        this.userRepository.save(oldUser);
        return new RedirectView("/admin/viewUsers");
    }

    @GetMapping("delete/{userID}")
    @Transactional
    public RedirectView deleteAppointment(@PathVariable Integer userID,HttpSession session) {
        if(session.getAttribute("email")==null)
        {
            return new RedirectView("/error");
        
        }
        User currUser = this.userRepository.findByUserID(userID);
        this.userRepository.delete(currUser);

        return new RedirectView("/admin/viewUsers");
    }

    public String hashpassword(String password) {
        String encoddedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return encoddedPassword;
    }

    @GetMapping("/addadmin")
    public ModelAndView showAdminForm(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("addadmin.html");
        Admin newAdmin=new Admin();
        mav.addObject("newAdmin", newAdmin);
        return mav;
    }

    @PostMapping("addadmin")
    public ModelAndView processAdminForm(@Valid @ModelAttribute("newAdmin") Admin newAdmin, BindingResult result,
    @RequestParam("cpassword") String confirmPass) {
        ModelAndView SignupModel = new ModelAndView("addadmin");
        ModelAndView refresh = new ModelAndView("CSHOME");

        if (adminRepository.findByEmail(newAdmin.getEmail())!=null) {
            result.rejectValue("email", "error.patient", "Email already exists. Please choose a different email.");
        }

    
        if (!newAdmin.getPass().equals(confirmPass)) {
            result.rejectValue("pass", "error.patient", "Password and Confirm Password must match.");
        }
    
        if (newAdmin.getPass().length() < 8) {
            result.rejectValue("pass", "error.patient", "Password must be at least 8 characters long.");
        }
    
        if (newAdmin.getPass().isEmpty()) {
            result.rejectValue("pass", "error.patient", "Password is required.");
        }
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("addadmin");
            mav.addObject("newAdmin", newAdmin);
            return mav;
        }
        Admin existingAdmin=adminRepository.findByEmail(newAdmin.getEmail());

        if (existingAdmin != null) {
            return SignupModel;
        } else {
            Admin admin=newAdmin.getAdmin();
            String encodedPassword = hashpassword(newAdmin.getPass());
            admin.setPass(encodedPassword);
            this.adminRepository.save(admin);
            return new ModelAndView("redirect:/admin/home");

        }
    }

    @GetMapping("/viewAdmins")
    public ModelAndView showAddadminsForm(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("viewadmins");
        Admin admin=new Admin();
        List<Admin> alladmins=adminRepository.findAll();
        mav.addObject("alladmins", alladmins);

        return mav;
    }


    @GetMapping("deleteadmin/{AdminID}")
    @Transactional
    public RedirectView deleteAssetType(@PathVariable Integer AdminID,HttpSession session) {
        if(session.getAttribute("email")==null)
        {
            return new RedirectView("/error");
        
        }
        AssetType currAssetType=this.assetTypeRepository.findByAssettypeid(AdminID);
        Admin currAdmin=this.adminRepository.findByAdminID(AdminID);
        this.adminRepository.delete(currAdmin);
        return new RedirectView("/admin/viewAdmins");
    }
    

    @GetMapping("/stock")
    public ModelAndView showStockForm(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("stock");

        // Find distinct asset types
        List<AssetType> assetTypes = assetRepository.findDistinctAssetTypes();

        // Create a list to hold statistics for each asset type
        List<AssetStat> assetStatisticsList = new ArrayList<>();

        for (AssetType assetType : assetTypes) {
            long totalAssets = assetRepository.countByAssetType(assetType);
            long usedAssets = assignedAssetRepository.countByAsset_AssetType(assetType);
            long availableAssets = totalAssets - usedAssets;

            // Create an AssetStatistics object and add it to the list
            AssetStat stats = new AssetStat(assetType, totalAssets, usedAssets, availableAssets);
            assetStatisticsList.add(stats);
        }

        // Add the assetStatistics list to the ModelAndView
        mav.addObject("assetStatisticsList", assetStatisticsList);

        return mav;
    }

    @GetMapping("/addDepartment")
    public ModelAndView DepartmentForm(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("addDepartment");
        Department newDepartment=new Department();
        mav.addObject("newDepartment", newDepartment);
        return mav;
    }
    @PostMapping("addDepartment")
    public ModelAndView processSignupForm(@Valid @ModelAttribute("newDepartment") Department newDepartment, BindingResult result) {
        ModelAndView SignupModel = new ModelAndView("addDepartment");
        ModelAndView refresh = new ModelAndView("CSHOME.html");
        System.out.println("------------------d--ddd--------ddd-"+newDepartment.getDepartmentname());
        Department department=newDepartment.getDepartment();
        department.setDepartmentname(newDepartment.getDepartmentname());
        Department existingDepartment=departmentRepository.findByDepartmentname(newDepartment.getDepartmentname());
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("addDepartment");
            mav.addObject("newDepartment", newDepartment);
            return mav;
        }
        if (existingDepartment != null) {
            return SignupModel;
        } else {

            this.departmentRepository.save(department);
            return new ModelAndView("redirect:/admin/viewDepartments");

        }
    }

    @GetMapping("/viewDepartments")
    public ModelAndView viewDepartmentsForm(HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("viewDepartments");
        List<Department> departments=departmentRepository.findAll();
        mav.addObject("departments", departments);
        return mav;
    }
    @GetMapping("editdepartment/{departmentID}")
    public ModelAndView editDepartment(@PathVariable Integer departmentID, HttpSession session) {
        if(session.getAttribute("email")==null)
        {
        ModelAndView mav = new ModelAndView("error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("editdepartment");
        Department oldDepartment = this.departmentRepository.findByDepartmentID(departmentID);
        System.out.println("-------------------------------------the department sent in the edit form :" + departmentID);
        mav.addObject("oldDepartment", oldDepartment);
        return mav;
    }
    
    @PostMapping("editdepartment/{departmentID}")
    public ModelAndView editDepartmentForm(@ModelAttribute("oldDepartment") Department oldDepartment, 
                                           BindingResult bindingResult, 
                                           @PathVariable Integer departmentID) {
        // if (bindingResult.hasErrors()) {
        //     ModelAndView mav = new ModelAndView("editdepartment");
        //     mav.addObject("oldDepartment", oldDepartment);
        //     return mav;
        // }
        
        // oldDepartment.setDepartmentID(departmentID);
       
        Department newDepartment=this.departmentRepository.findByDepartmentID(departmentID);
        System.out.println(newDepartment.getDepartmentname());
        newDepartment.setDepartmentname(oldDepartment.getDepartmentname());
        this.departmentRepository.save(newDepartment);
        return new ModelAndView("redirect:/admin/viewDepartments");
    }
    
    

    @GetMapping("deletedepartment/{departmentID}")
    @Transactional
    public RedirectView deleteDepartment(@PathVariable Integer departmentID,HttpSession session) {
        if(session.getAttribute("email")==null)
        {
            return new RedirectView("/error");
        
        }
        Department currDepartment=this.departmentRepository.findByDepartmentID(departmentID);
        this.departmentRepository.delete(currDepartment);
        return new RedirectView("/admin/viewDepartments");
    }

    @GetMapping("/Login")
    public ModelAndView Login() {
        ModelAndView mav = new ModelAndView("Login.html");
        return mav;
    }

    @PostMapping("/Login")
    public RedirectView loginprocess(@RequestParam("email") String email, @RequestParam("pass") String pass,
            HttpSession session) {
        Admin newUser = this.adminRepository.findByEmail(email);

        if (newUser != null) {
            Boolean PasswordsMatch = BCrypt.checkpw(pass, newUser.getPass());
            if (PasswordsMatch) {
                session.setAttribute("email", newUser.getEmail());
                session.setAttribute("adminID", newUser.getAdminID());
                session.setAttribute("Firstname", newUser.getFirstname());
                session.setAttribute("Lastname", newUser.getLastname());
                session.setAttribute("number", newUser.getNumber());
                return new RedirectView("/admin/home");

            } else {
                return new RedirectView("/User/Login?error=incorrectPassword  " + email);
            }
        }
        return new RedirectView("/User/Login?error=userNotFound  " + email);
    }

    

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/");
    }
}
