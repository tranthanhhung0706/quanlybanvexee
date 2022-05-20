package poly.controller;

import javax.servlet.http.HttpSession;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ptithcm.Entity.Dia_Diem;


@Controller
@RequestMapping("site/")
@Transactional
public class HomeController {
	 @Autowired
	 SessionFactory factory;
     @RequestMapping("index")
     public String index(ModelMap model,HttpSession session) {
    	 List<Dia_Diem> list=this.getdiadiem();
    	 session.setAttribute("tatCaDiaDiem", list);
    	 return "site/index";
     }
     public List<Dia_Diem> getdiadiem(){
    	 Session session=factory.getCurrentSession();
    	 String hql="from Dia_Diem ";
    	 Query query=session.createQuery(hql);
    	 List<Dia_Diem> list=query.list();
    	 return list;
     } 
    @RequestMapping("redirect")
 	public String logout(HttpSession ss) {
 		System.out.println("logout");
 		ss.removeAttribute("tk_nv");
 		ss.removeAttribute("tk_kh");
 		ss.removeAttribute("tai_khoans");
 		return "redirect:index.htm";
 	}
     
}
