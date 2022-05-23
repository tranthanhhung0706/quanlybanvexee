package poly.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.Entity.Account;
import ptithcm.Entity.Nhan_Vien;
import ptithcm.Entity.Role;
import ptithcm.Entity.User;
import ptithcm.bean.Mailer;

@Controller
@RequestMapping("site/")
@Transactional
public class AuthenticationController {
	@Autowired
	SessionFactory factory;
	static Account accounttemp;
	static User usertemp;
    @RequestMapping("sign_in")
    public String sign_in() {
    	return "site/login";
    }
    @RequestMapping(value = "sign_in",method = RequestMethod.POST)
    public String signin(ModelMap model,HttpSession session,HttpServletRequest request, @Validated @ModelAttribute("Account") Account account, BindingResult errors ) {
    	
//    	if(account.getUsername().trim().length() == 0) {
//			errors.rejectValue("username", "Account", "Vui long nhap ho ten !");
//		}
//		if(account.getPassword()== null) {
//			errors.rejectValue("password", "Account", "Vui long nhap mat khau !");
//		}
//		
//		
//		String captcha = session.getAttribute("captcha_security").toString();
//		String verifyCaptcha = request.getParameter("captcha");
//
//		// boolean verify = RecaptchaVerification.verify(gRecaptchaResponse);
//		boolean verify = false;
//		if (captcha.equals(verifyCaptcha)) {
//			verify = true;
//		} else {
//			verify = false;
//		}
//
//		if (errors.hasErrors() || !verify) {
//
//
//			if (!verify) {
//				model.addAttribute("reCaptra", "Vui lòng nhâp reCaptra");
//				// System.out.println("có lổi Passwword");
//			}
//
//			return "site/login";
//		} else {
//			System.out.println("Không có lổi Đăng nhâp !");
//			
//		}
		
		
		try {
			accounttemp=this.gettk(request.getParameter("username"), request.getParameter("password"));
			if(accounttemp !=null) {
				session.setAttribute("tai_khoans",this.gettk(request.getParameter("username"), request.getParameter("password")) );
				System.out.println(accounttemp.getIdRole().getAuthority());
				if(accounttemp.getIdRole().getAuthority().equals("ROLE_EMPLOYEE")) {
					System.out.println(1);
					session.setAttribute("tk_nv", this.getnv(accounttemp.getAccountId()));
					
				}else {
					System.out.println(0);
					session.setAttribute("tk_kh", this.getuser(accounttemp.getAccountId()));
					
				}
//				session.setAttribute("tks", this.getuser(accounttemp.getAccountId()));
//				System.out.println(this.getuser(accounttemp.getAccountId()));
			}
			return "site/index";
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("message","account do not exits");
			return "site/login";
		}
		
    	
    	
    	
    }
    @RequestMapping("register")
    public String register2(ModelMap model,@ModelAttribute("tks") Account tk) {
//    	model.addAttribute("tks",new Account());
    	return "site/register";
    }
//    hung
    @Autowired
    Mailer mailer;
    static String codeTemp;
    @RequestMapping(value = "forgotPassword",params = "sendcode")
    public String send(ModelMap model,@ModelAttribute("khach_hang") Account kh,HttpServletRequest request) {
    	String to=request.getParameter("email");
    	String from="VE";
    	String subject="Validation code";
    	Random rd=new Random();
    	Integer number=rd.nextInt(1000);
    	String body=Integer.toString(number);
    	codeTemp=body;
    	try {
			mailer.send(from, to, subject, body);
			model.addAttribute("message","Code send!");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("message","Code send Failed");
		}
    	return "site/user/forgotpassword";
    }
    @RequestMapping("forgotPassword")
    public String forgot(ModelMap model,@ModelAttribute("khach_hang") Account kh) {
    	return "site/user/forgotpassword";
    }
    public Account gettkuser(String username) {
    	Session session=factory.getCurrentSession();
    	String hql="from Account where username =:username";
    	Query query=session.createQuery(hql);
    	query.setParameter("username", username);
    	Account list=(Account) query.list().get(0);
    	return list;
    }
    @RequestMapping(value = "forgotPassword",params = "change",method = RequestMethod.POST)
    public String forgot(ModelMap model,@ModelAttribute("khach_hang") Account kh,@RequestParam("confirm") String password,@RequestParam("email") String email,@RequestParam("code") String code,@ModelAttribute("username") String username) {
    	
			if(kh.getUsername()=="") {
				model.addAttribute("message","vui long nhap username");
			}else if(email=="") {
				model.addAttribute("message","Vui long nhap email");
			}else if(kh.getUsername() !="" && email !="") {
    	    accounttemp=this.gettkuser(kh.getUsername());
    	    kh.setAccountId(accounttemp.getAccountId());
    	    kh.setIdRole(accounttemp.getIdRole());
    	    kh.setAccountState(accounttemp.getAccountState());
				if(kh.getPassword() =="") {
					model.addAttribute("message","vui long nhap mat khau moi");
				}else {
					if(code.equals(codeTemp)) {
						if(kh.getPassword().equals(password)) {
    	                Integer check=this.updatetk(kh);
							if(check==1) {
								model.addAttribute("message","thay doi thanh cong");
							}else {
								model.addAttribute("message","thay doi that bai");
							}
						}else {
							model.addAttribute("message","xac nhan password ko dung");
						}
					}else {
						model.addAttribute("message","Code invalid");
					}
				}
			}
//    	usertemp=this.getsdtuser(kh.getPhoneNumber());
//    	
//        kh.setUserId(usertemp.getUserId());
//        System.out.println(kh.getPhoneNumber());
//        System.out.println(usertemp);
//        Integer check=this.updateuser(kh);
//        if(check==1) {
//			model.addAttribute("message","thay doi thanh cong");
//		}else {
//			model.addAttribute("message","thay doi that bai");
//		}
//    	accounttemp=this.gettkuser(kh.getUsername());
//    	kh.setAccountId(accounttemp.getAccountId());
//    	kh.setIdRole(accounttemp.getIdRole());
//    	Integer check=this.updatetk(kh);
//        if(check==1) {
//			model.addAttribute("message","thay doi thanh cong");
//		}else {
//			model.addAttribute("message","thay doi that bai");
//		}
    	return "site/user/forgotpassword";
    }
    @RequestMapping(value = "sign_up",method = RequestMethod.POST)
    public String register(ModelMap model,@ModelAttribute("tks") Account tk, @ModelAttribute("role") Role role,@ModelAttribute("kh") User kh
    		,@RequestParam("name") String name
    		,@RequestParam("gioitinh") String gioitinh
    		,@RequestParam("email") String email,@RequestParam("nghenghiep") String nghenghiep
    		,@RequestParam("cmnd") String cmnd,@RequestParam("ngaysinh") String ngaysinh
    		,@RequestParam("sdt") String sdt,@RequestParam("diachi") String diachi, BindingResult errors) {
    	int check=0;
    	if(sdt.trim().equals("")) {
    		check++;
    		model.addAttribute("sdterror","Nhập số điện thoại");
    	}else if(!sdt.matches("09[0-9]{8}")) {
    		check++;
    		model.addAttribute("sdterror","Số điện thoại không hợp lệ");
    	}
    	if(name.trim().equals("")) {
    		check++;
    		model.addAttribute("hotenerror","Nhập họ tên");
    	}
    	if(tk.getPassword().trim().equals("")) {
    		check++;
    		model.addAttribute("passerror","Nhập mật khẩu");
    	}else if(tk.getPassword().trim().length() <6) {
    		check++;
    		model.addAttribute("passerror","Mật khẩu ít nhất 6 kí tự");
    	}
    	if(check!=0)  return "site/register";
    	tk.setAccountState(1);
    	tk.setUsername(sdt);
    	
    	kh.setPhoneNumber(tk.getUsername());
   
    	kh.setHoTen(name);
    	kh.setGioiTinh(gioitinh);
    	kh.setCmnd(cmnd);
    	kh.setEmail(email);
    	kh.setNgaySinh(ngaysinh);
    	kh.setNgheNghiep(nghenghiep);
    	kh.setDiaChi(diachi);
    	
    	role.setUsername(sdt);
    	role.setAuthority("ROLE_USER");
    	//role.getId();
    	tk.setIdRole(role);
    	kh.setIdTaiKhoan(tk);
		Integer check3=this.saverole(role);
    	Integer check1=this.savetk(tk);
    	
    	Integer check2=this.savekh(kh);
    	if(check1==1 && check2==1 &&check3==1 ) {
    		model.addAttribute("message","dang ki thanh cong");
    		return "site/register";
    	}else {
    		model.addAttribute("message","dang ki that bai");
    		return "site/register";
    	}
    }
    public Integer savekh(User kh) {
    	Session session=factory.openSession();
    	Transaction t=session.beginTransaction();
    	try {
			session.save(kh);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		}
    	return 1;
    }
    public Integer saverole(Role role) {
    	Session session=factory.openSession();
    	Transaction t=session.beginTransaction();
    	try {
			session.save(role);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		}
    	return 1;
    }
    public Integer savetk(Account tk) {
    	Session session=factory.openSession();
    	Transaction t=session.beginTransaction();
    	try {
			session.save(tk);
			t.commit();
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			return 0;
		}
    	return 1;
    }
    
    public User getsdtuser(String sdt) {
    	Session session=factory.getCurrentSession();
    	String hql="from User WHERE phoneNumber =:sdt";
    	Query query=session.createQuery(hql);
    	query.setParameter("sdt", sdt);
    	User list=(User) query.list().get(0);
    	return list;
    }
    public Integer updateuser(User tk) {
    	Session session=factory.openSession();
    	Transaction t=session.beginTransaction();
    	try {
			session.update(tk);
			t.commit();
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			return 0;
		}finally {
			session.close();
		}
    	return 1;
    }
    public Integer updatetk(Account tk) {
    	Session session=factory.openSession();
    	Transaction t=session.beginTransaction();
    	try {
			session.update(tk);
			t.commit();
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			return 0;
		}finally {
			session.close();
		}
    	return 1;
    }
    public Account gettk(String username,String password) {
    	Session session=factory.getCurrentSession();
    	String hql="from Account where username =:username and password =:password";
    	Query query=session.createQuery(hql);
    	query.setParameter("username", username);
    	query.setParameter("password", password);
    	Account list=(Account) query.list().get(0);
    	return list;
    }
    public User getuser(Integer idtk) {
    	Session session=factory.getCurrentSession();
    	String hql="from User where id_tai_khoan =:idtk";
    	Query query=session.createQuery(hql);
    	query.setParameter("idtk", idtk);
    	User list=(User) query.list().get(0);
    	return list;
    }
    public Nhan_Vien getnv(Integer idtk) {
    	Session session=factory.getCurrentSession();
    	String hql="from Nhan_Vien where id_tai_khoan =:idtk";
    	Query query=session.createQuery(hql);
    	query.setParameter("idtk", idtk);
    	Nhan_Vien list=(Nhan_Vien) query.list().get(0);
    	return list;
    }
    
}
