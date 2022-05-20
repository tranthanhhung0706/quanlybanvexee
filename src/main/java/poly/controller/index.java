//package poly.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import ptithcm.Entity.Dia_Diem;
//
//@Controller
//@RequestMapping("")
//@Transactional
//public class index {
//	@Autowired
//	SessionFactory factory;
//	@RequestMapping("index")
//    public String index(ModelMap model,HttpSession session) {
//	 List<Dia_Diem> list=this.getdiadiem();
//   	 session.setAttribute("tatCaDiaDiem", list);
//
//    	return "index";
//    }
//	  public List<Dia_Diem> getdiadiem(){
//	    	 Session session=factory.getCurrentSession();
//	    	 String hql="from Dia_Diem ";
//	    	 Query query=session.createQuery(hql);
//	    	 List<Dia_Diem> list=query.list();
//	    	 return list;
//	     }
//}
