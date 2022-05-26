package poly.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.Entity.Chuyen_Xe;
import ptithcm.Entity.Ghe;
import ptithcm.Entity.Loai_Xe;
import ptithcm.Entity.Nhan_Vien;
import ptithcm.Entity.Tuyen_Xe;
import ptithcm.Entity.Ve_Xe;
import ptithcm.Entity.Xe;

@Controller
@RequestMapping("site/")
@Transactional
public class NhanVienController {
   @Autowired
   SessionFactory factory;
   static Nhan_Vien nhanvientemp;
   @RequestMapping(value = "in_for2/{idNhanVien}.htm",params = "btnid2")
   public String infornv(ModelMap model,@PathVariable("idNhanVien") Integer idNhanVien,@ModelAttribute("nhan_vien") Nhan_Vien nhan_vien ) {
	   Nhan_Vien list=this.getnv(idNhanVien);
	   nhanvientemp=this.getnv(idNhanVien);
	   model.addAttribute("nhan_viens",list);
	   model.addAttribute("nhan_vien",this.getnv(idNhanVien));
	   return "site/user/userInfo";
   }
   @RequestMapping("nghiepvu")
   public String nghiepvu(ModelMap model) {
	   return "site/nhanvien/nghiepVu";
   }
   @RequestMapping("themxe")
   public String themxe(ModelMap model) {
	   model.addAttribute("xe",new Xe());
	   return "site/nhanvien/themxe";
   }
   @RequestMapping(value = "themxe",method = RequestMethod.POST)
   public String themxemoi(ModelMap model,@ModelAttribute("xe") Xe xe) {
	   Loai_Xe loaiXe =this.getLoaiXeFromId(xe.getMaLoaiXe().getIdLoaiXe());
	   List<Ghe> gheList = new ArrayList<Ghe>();
		//Thêm ghế vào xe
		String gheA = "A";
		String gheB = "B";
		for (int i = 1; i <= 12; i++) {
			if (i < 10) {
					gheA = gheA.concat("0" + Integer.toString(i));
					gheB = gheB.concat("0" + Integer.toString(i));
					Ghe tempGheA = new Ghe(gheA);
					Ghe tempGheB = new Ghe(gheB);
					gheList.add(tempGheA);
					gheList.add(tempGheB);
					gheA = "A";
					gheB = "B";
			} else {
					gheA = gheA.concat(Integer.toString(i));
					gheB = gheB.concat(Integer.toString(i));
					Ghe tempGheA = new Ghe(gheA);
					Ghe tempGheB = new Ghe(gheB);
					gheList.add(tempGheA);
					gheList.add(tempGheB);
					gheA = "A";
					gheB = "B";
			}
		}
		if (xe.getMaLoaiXe().getIdLoaiXe() == 2) {// Ghế ngồi thì thêm mỗi tầng 6 ghế nữa
			gheA = "A";
			gheB = "B";
			for (int i = 13; i <= 18; i++) {
				
				gheA = gheA.concat(Integer.toString(i));
				gheB = gheB.concat(Integer.toString(i));
				Ghe tempGheA = new Ghe(gheA);
				Ghe tempGheB = new Ghe(gheB);
				gheList.add(tempGheA);
				gheList.add(tempGheB);
				gheA = "A";
				gheB = "B";
			}
		}
		xe.setMaLoaiXe(loaiXe);
		xe.setGheList(gheList);
	   Integer check=this.savexe(xe);
	   if(check==1) {
		   model.addAttribute("message","them  xe thanh cong");
	   }else {
		   model.addAttribute("message","them xe that bai");
	   }
	   return "site/nhanvien/themxe";
   }
   @RequestMapping("chuyen_xe_hoan_thanh")
   public String chuyenxeht(ModelMap model) {
	   model.addAttribute("listChuyenXeChuaHoanThanh",this.getchuyenxe());
	   model.addAttribute("chuyenxe",new Chuyen_Xe());
	   return "site/nhanvien/chuyen_xe_hoan_thanh";
   }
   @RequestMapping(value = "danhdauxe",method = RequestMethod.POST)
   public String danhdauxe(ModelMap model,@ModelAttribute("chuyenxe") Chuyen_Xe chuyenxe) {
	   chuyenxe.setMaXe(this.getchuyenxe1(chuyenxe.getMaChuyen()).getMaXe());
	   chuyenxe.setMaTuyen(this.getchuyenxe1(chuyenxe.getMaChuyen()).getMaTuyen());
	   chuyenxe.setGioChay(this.getchuyenxe1(chuyenxe.getMaChuyen()).getGioChay());
	   chuyenxe.setDaHoanThanh(true);
	   Integer check=this.updatechuyenxe(chuyenxe);
	   if(check==1) {
		   model.addAttribute("message","da hoan thanh");
	   }else {
		   model.addAttribute("message","hoan thanh that bai");
	   }
	   model.addAttribute("listChuyenXeChuaHoanThanh",this.getchuyenxe());
	   return "site/nhanvien/chuyen_xe_hoan_thanh";
   }
   @RequestMapping("themchuyenxe")
   public String themchuyenxe(ModelMap model) {
	   model.addAttribute("chuyenxe",new Chuyen_Xe());
	   model.addAttribute("tuyenXeList",this.gettuyenxe());
	   return "site/nhanvien/them_chuyen_xe";
   }
   @RequestMapping(value = "themchuyenxe",method = RequestMethod.POST)
   public String themchuyenxe1(ModelMap model,@ModelAttribute("chuyenxe") Chuyen_Xe chuyenxe,@RequestParam("tuyenXe")String tuyenXe, @RequestParam("ngayChay") String ngayChay,@RequestParam("hh")String hh,
			  @RequestParam("mm")String mm) {
	   String[] idDiaDiem =tuyenXe.split(",");
	   System.out.println(Integer.parseInt(idDiaDiem[0]));
	   System.out.println(Integer.parseInt(idDiaDiem[1]));
	   Tuyen_Xe tempTuyenXe=this.gettuyenxe(Integer.parseInt(idDiaDiem[0]),Integer.parseInt(idDiaDiem[1]));
	   System.out.print(tempTuyenXe);
	   
	   String giochay=ngayChay+" "+hh+":"+mm;
	   chuyenxe.setMaTuyen(tempTuyenXe);
	   chuyenxe.setGioChay(giochay);
	   Integer check=this.savethemchuyenxe(chuyenxe);
	   if(check==1) {
		   model.addAttribute("message","them chuyen  xe thanh cong");
	   }else {
		   model.addAttribute("message","them chuyen xe that bai");
	   }
	   return "site/nhanvien/them_chuyen_xe";
   }
   public Tuyen_Xe gettuyenxe(Integer id_ddi,Integer id_dden) {
	   Session session=factory.getCurrentSession();
	   String hql="FROM Tuyen_Xe TX WHERE TX.diaDiemDi.idDiaDiem = :id_ddi" + 
				" AND TX.diaDiemDen.idDiaDiem = :id_dden";
	   Query query=session.createQuery(hql);
	   query.setParameter("id_ddi", id_ddi);
	   query.setParameter("id_dden", id_dden);
	   Tuyen_Xe list=(Tuyen_Xe) query.list().get(0);
	   return list;
   }
   public Loai_Xe getLoaiXeFromId(Integer idLoaiXe ) {
	   Session session=factory.getCurrentSession();
	   String hql="from Loai_Xe where idLoaiXe =:idloaixe";
	   Query query=session.createQuery(hql);
	   query.setParameter("idloaixe", idLoaiXe);
	   Loai_Xe list=(Loai_Xe)query.list().get(0);
	   return list;
   }
   public Integer savethemchuyenxe(Chuyen_Xe chuyenxe) {
	   Session session=factory.openSession();
	   Transaction t=session.beginTransaction();
	   try {
		session.save(chuyenxe);
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
   public Integer updatechuyenxe(Chuyen_Xe chuyenxe) {
	   Session session=factory.openSession();
	   Transaction t=session.beginTransaction();
	   try {
		  session.update(chuyenxe);
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
   public Chuyen_Xe getchuyenxe1(Integer machuyen) {
	   Session session=factory.getCurrentSession();
	   String hql="from Chuyen_Xe where maChuyen =:machuyen";
	   Query query=session.createQuery(hql);
	   query.setParameter("machuyen", machuyen);
	   Chuyen_Xe list=(Chuyen_Xe) query.list().get(0);
	   return list;
   }
   public List<Chuyen_Xe> getchuyenxe(){
	   Session session=factory.getCurrentSession();
	   String hql="From Chuyen_Xe where daHoanThanh =:dahoanthanh";
	   Query query=session.createQuery(hql);
	   query.setParameter("dahoanthanh", 0);
	   List<Chuyen_Xe> list=query.list();
	   return list;
   }
   public Integer savexe(Xe xe) {
	   Session session=factory.openSession();
	   Transaction t=session.beginTransaction();
	   try {
		 session.save(xe);
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
   @RequestMapping(value = "update2",params = "btnEdit2")
   public String update2(ModelMap model,@ModelAttribute("nhan_vien") Nhan_Vien nhan_vien) {
	   try {
		   nhan_vien.setIdTaiKhoan(nhanvientemp.getIdTaiKhoan());
		   nhan_vien.setDaNghiViec(nhanvientemp.getDaNghiViec());
		   Integer check=this.updatenv(nhan_vien);
		   if(check==1) {
			   model.addAttribute("messsage","update thanh cong");
		   }else {
			   model.addAttribute("message","update that bai");
		   }
		   model.addAttribute("nhan_viens",this.getnv(nhan_vien.getIdNhanVien()));
	} catch (Exception e) {
		// TODO: handle exception
		return "site/user/userInfo";
	}
	   return "site/user/userInfo";
   }
   @RequestMapping(value = "thanh_toan/{idNhanVien}.htm",params = "btnid3")
   public String thanhtoan(ModelMap model,@PathVariable("idNhanVien") Integer idNhanVien) {
	   
	   List<Ve_Xe> list=this.getves();
	   System.out.println(list);
	   model.addAttribute("trang_thai_ve",list);
	   return "site/nhanvien/veChuaThanhToanList";
   }
   @RequestMapping(value="timkiem2",params = "tim")
   public String timkiem(ModelMap model,@RequestParam("maVeInput") Integer mave,HttpServletRequest request) {
	   Ve_Xe list1=this.getve1(mave);
	   model.addAttribute("listve1",list1);
	   System.out.println(request.getParameter("tuyenDuongInput"));
	   return "site/nhanvien/veChuaThanhToanList";
   }
   
   @RequestMapping(value="timkiem2",params = "lammoi")
   public String timkiem(ModelMap model) {
	   List<Ve_Xe> list=this.getves();
	   System.out.println(list);
	   model.addAttribute("trang_thai_ve",list);
	   return "site/nhanvien/veChuaThanhToanList";
   }
   
   @ModelAttribute("loaixesel")
   public List<Loai_Xe> getloaixe(){
	   Session session=factory.getCurrentSession();
	   String hql="from Loai_Xe";
	   Query query=session.createQuery(hql);
	   List<Loai_Xe> list=query.list();
	   return list;
   }
   @ModelAttribute("bienxesel")
   public List<Xe> getXe(){
	   Session session=factory.getCurrentSession();
	   String hql="from Xe";
	   Query query=session.createQuery(hql);
	   List<Xe> list=query.list();
	   return list;
   }
   @ModelAttribute("tuyenxesel")
   public List<Tuyen_Xe> gettuyenxe(){
	   Session session=factory.getCurrentSession();
	   String hql="from Tuyen_Xe";
	   Query query=session.createQuery(hql);
	   List<Tuyen_Xe> list=query.list();
	   return list;
   }
   public Ve_Xe getve1(Integer idve) {
	   Session session=factory.getCurrentSession();
	   String hql="from Ve_Xe where idVe =:idve";
	   Query query=session.createQuery(hql);
	   query.setParameter("idve", idve);
	   Ve_Xe list=(Ve_Xe) query.list().get(0);
	   return list;
   }
   
  
   
   public List<Ve_Xe> getves(){
	   Session session=factory.getCurrentSession();
	   String hql="from Ve_Xe where trangThai =:trang_thai1 or trangThai =:trang_thai2";
	   Query query=session.createQuery(hql);
	   query.setParameter("trang_thai1", "Chờ thanh toán");
	   query.setParameter("trang_thai2", "Đã hủy");
	   List<Ve_Xe> list=query.list();
	   return list;
   }
   public Integer updatenv(Nhan_Vien nv) {
	   Session session=factory.openSession();
	   Transaction t=session.beginTransaction();
	   try {
		   session.update(nv);
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
   public Nhan_Vien getnv(Integer idnv) {
	   Session session=factory.getCurrentSession();
	   String hql="from Nhan_Vien where idNhanVien=:idnv ";
	   Query query=session.createQuery(hql);
	   query.setParameter("idnv", idnv);
	   Nhan_Vien list=(Nhan_Vien) query.list().get(0);
	   return list;
   }
}
