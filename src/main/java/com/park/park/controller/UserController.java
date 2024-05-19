package com.park.park.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.zxing.WriterException;
import com.park.park.model.Tampungan;
import com.park.park.model.User;
import com.park.park.repository.TampunganRepository;
import com.park.park.repository.UserRepository;
import com.park.park.utils.QrCode;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TampunganRepository tampunganRepository;

    @GetMapping("/home")
    public String home(){
        tampunganRepository.deleteAll();
            return "home";
    }
    @GetMapping("/register")
    public String register(Model model){
        tampunganRepository.deleteAll();
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/save")
    public String save(User user) throws WriterException, IOException{
        if(tampunganRepository.findAll() != null){
            User user2 = userRepository.findByNim(user.getNim());
            if(user2 != null){
                return "daftar-gagal";
            }
            try{
                user.setImage(QrCode.generateQrcode(user));
                userRepository.save(user);
                return "redirect:/login";
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return "redirect:/qrcode";
    }
    @GetMapping("/login")
    public String login(){
        tampunganRepository.deleteAll();
            return "login";
    }
    @PostMapping("/cek-login")
    public String cekLogin(@RequestParam(name = "username") String username,@RequestParam(name = "password") String pass){
        User user = userRepository.findByNim(username);
        if(user == null){
            return "gagal-login";
        }
        if(user.getPassword().equals(pass)){
            Tampungan tampungan = new Tampungan();
            tampungan.setImage(user.getImage());
            tampungan.setPassword(pass);
            tampungan.setUsername(username);
            tampunganRepository.save(tampungan);
            return "redirect:/qrcode";
        }
        return "gagal-login";
    }
    @GetMapping("/qrcode")
    public String qrCode(Model model){
        if (tampunganRepository.findAll() == null) { 
            return "redirect:/home";
        }
        List<Tampungan> tampungan = tampunganRepository.findAll();
        User user = userRepository.findByNim(tampungan.get(0).getUsername());
        model.addAttribute("user",user);
        return "qrcode";
    }
    @GetMapping("/logout")
    public String logout(){
        List<Tampungan> tampungans = tampunganRepository.findAll();
        if(tampungans == null){
            return "redirect:/home";
        }
        tampunganRepository.deleteAll();
        return "redirect:/home";
    }
}


