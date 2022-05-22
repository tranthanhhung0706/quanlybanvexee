package poly.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.Entity.Ghe;
import ptithcm.Entity.Ve_Xe;
import ptithcm.Entity.Xe;
import ptithcm.Entity.Chuyen_Xe;
import ptithcm.Entity.Tuyen_Xe;
import ptithcm.Entity.User;

@Controller
@RequestMapping("site/")
@Transactional
public class Ve_XeController {
   @Autowired
   SessionFactory factory;
   @RequestMapping("step2/{userId}.htm")
   public String step2(ModelMap model,@RequestParam("loaive") String loaiVe,
		   @RequestParam("chonDiemDi") int idDiaDiemDi,@RequestParam("chonDiemDen") int idDiemDen,
		   @RequestParam("chonNgayDi") String ngayDi,
		   @RequestParam(value = "chonNgayVe",required = false) String ngayVe,HttpServletRequest request,HttpSession session,
		   @PathVariable("userId") Integer userId) {
//	   session.setAttribute("loaiVe", loaiVe);
	   request.getSession().setAttribute("loaiVe", loaiVe);
	   model.addAttribute("loaiVe",loaiVe);
	   if(loaiVe.equals("khuhoi")) {
		   model.addAttribute("ngayVe",ngayVe);
	   }
	   Tuyen_Xe tuyenXeCanTim=this.gettuyenxe(idDiaDiemDi, idDiemDen);
	   model.addAttribute("tuyenXeCanTim", tuyenXeCanTim);
	   model.addAttribute("ngayDi", ngayDi);
	   List<Chuyen_Xe> chuyenXeCanTim=this.getChuyenXeThoaMan(ngayDi, tuyenXeCanTim.getIdTuyen());
	   model.addAttribute("chuyenXeCanTim", chuyenXeCanTim);
	   HashMap<Integer, List<String>> hashMapDaXuLiGioDiVaGioDenTuongUng = new HashMap<Integer, List<String>>();
		for (Chuyen_Xe chuyenXe : chuyenXeCanTim) {
			String[] temp = chuyenXe.getGioChay().split(" ")[1].split(":");

			String gioBanDau = temp[0] + ":" + temp[1];
			int GioSauKhongChuaSoKhongBanDau = (((Integer.parseInt(temp[0])) + tuyenXeCanTim.getThoiGianTon()) % 24);
			String gioSauDaCoSoKhong = temp[0];
			if (GioSauKhongChuaSoKhongBanDau < 10)
				gioSauDaCoSoKhong = "0".concat(Integer.toString(GioSauKhongChuaSoKhongBanDau));
			else {
				gioSauDaCoSoKhong = Integer.toString(GioSauKhongChuaSoKhongBanDau);
			}
			gioSauDaCoSoKhong = gioSauDaCoSoKhong.concat(":" + temp[1]);
			List<String> thoiGianList = new ArrayList<String>();
			thoiGianList.add(gioBanDau);
			thoiGianList.add(gioSauDaCoSoKhong);

			hashMapDaXuLiGioDiVaGioDenTuongUng.put(chuyenXe.getMaChuyen(), thoiGianList);
		}
		model.addAttribute("hashMapDaXuLiGio", hashMapDaXuLiGioDiVaGioDenTuongUng);
		HashMap<Integer, List<Ghe>> hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe = new HashMap<Integer, List<Ghe>>();
		for (Chuyen_Xe chuyenXe : chuyenXeCanTim) {
			List<Ve_Xe> veXeList = chuyenXe.getVeXeList();

			List<Ghe> listCacGheDaDatTuongUngVoiMoiChuyenXe = new ArrayList<Ghe>();
			for (Ve_Xe veXe : veXeList) {
				if (!veXe.getTrangThai().equals("Đã hủy")) {
					System.err.println(veXe.getTrangThai());
					List<Ghe> gheList = veXe.getGheList(); // đây là list các ghế đã đặt tương ứng với mỗi vé
					listCacGheDaDatTuongUngVoiMoiChuyenXe.addAll(gheList);
				}
			}
			hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe.put(chuyenXe.getMaChuyen(),
					listCacGheDaDatTuongUngVoiMoiChuyenXe); // Xử lí số chỗ còn lại của mỗi chuyến xe
		}

		model.addAttribute("hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe",
				hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe);
	   return "site/datve/step2";
   }
//   @RequestMapping("step3/{userId}.htm")
//   public String step3(ModelMap model,@PathVariable("userId") Integer userId) {
//	   return "site/datve/step3";
//   }
   @RequestMapping(value = "step3/{userId}.htm",method = RequestMethod.POST)
   public String step3(ModelMap model,@PathVariable("userId") Integer userId,@RequestParam("chuyenXe") int idChuyenXe,
			@RequestParam("gheCheckBox") List<Ghe> gheList,HttpServletRequest request) {
	   String loaiVe = (String) request.getSession().getAttribute("loaiVe");
		// -> Nếu loại vé là một chiều thì vé bên dưới là vé thứ nhất
		// ->nếu loại vé là khứ hồi thì vé này là vé thứ về, vé đi có tên là
		// veXeChinhThuc1 or veXeTam1 trong session
		// Để cho đỡ rối thì nên lặp code 1 tí

		if (loaiVe.equals("motchieu")) {
			Ve_Xe ve = new Ve_Xe();

			// khúc này là xác minh xem người hiện tại đang đặt vé là người dùng hay nhân
			// viên
			// Nếu là người dùng đã đăng kí rồi thì không cần lưu người dùng vào db vì đã có
			// sẵn
			
//			if (request.isUserInRole("ROLE_USER")) {
//				User tempUser = (User) request.getSession().getAttribute("user");
//				ve.setIdKhachHang(tempUser);
//			}
            User tempUser=this.getkh(userId);
            ve.setIdKhachHang(tempUser);
			Chuyen_Xe tempChuyenXe = this.getChuyenXe(idChuyenXe);
			ve.setIdChuyenXe(tempChuyenXe);
			ve.setGheList(gheList);
			Xe tempXe = tempChuyenXe.getMaXe();
			long tongTien = (tempChuyenXe.getMaXe().getMaLoaiXe().getTienVeMoiCho()) * gheList.size();
			ve.setTongTien(tongTien);

			if (tempUser.getIdTaiKhoan().getIdRole().getAuthority().equals("ROLE_USER")) {

				request.getSession().setAttribute("veXeChinhThuc", ve);
			} else {
				request.getSession().setAttribute("veXeTam", ve);
			}
		}
		// này nếu loại vé là khứ hồi
		else {
			Ve_Xe veThuHai = new Ve_Xe();

			// khúc này là xác minh xem người hiện tại đang đặt vé là người dùng hay nhân
			// viên
			// Nếu là người dùng đã đăng kí rồi thì không cần lưu người dùng vào db vì đã có
			// sẵn
			User tempUser=this.getkh(userId);
			if (tempUser.getIdTaiKhoan().getIdRole().getAuthority().equals("ROLE_USER")) {
//				User tempUser = (User) request.getSession().getAttribute("user");
				veThuHai.setIdKhachHang(tempUser);
				// lấy vé thứ nhất ra setIdKhachHang
				Ve_Xe veThuNhat = (Ve_Xe) request.getSession().getAttribute("veXeChinhThuc1");
				veThuNhat.setIdKhachHang(tempUser);
				request.getSession().setAttribute("veXeChinhThuc1", veThuNhat);

			}

			Chuyen_Xe tempChuyenXe = this.getChuyenXe(idChuyenXe);
			veThuHai.setIdChuyenXe(tempChuyenXe);
			veThuHai.setGheList(gheList);
			Xe tempXe = tempChuyenXe.getMaXe();
			long tongTien = (tempChuyenXe.getMaXe().getMaLoaiXe().getTienVeMoiCho()) * gheList.size();
			veThuHai.setTongTien(tongTien);

			if (tempUser.getIdTaiKhoan().getIdRole().getAuthority().equals("ROLE_USER")) {

				request.getSession().setAttribute("veXeChinhThuc", veThuHai);
			} else {
				request.getSession().setAttribute("veXeTam", veThuHai);
			}
		}
	   return "site/datve/step3";
   }
   @RequestMapping("step4")
   public String step4(ModelMap model,HttpServletRequest request) {
	   String loaiVe=(String) request.getSession().getAttribute("loaiVe");
	   if(loaiVe.equals("motchieu")) {
		   Ve_Xe veXe=(Ve_Xe) request.getSession().getAttribute("veXeChinhThuc");
		   model.addAttribute("veXe", veXe);
	   }else {
		   Ve_Xe veThuNhat=(Ve_Xe) request.getSession().getAttribute("veXeChinhThuc1");
		   Ve_Xe veThuHai=(Ve_Xe) request.getSession().getAttribute("veXeChinhThuc");
		   model.addAttribute("veXe",veThuNhat);
		   model.addAttribute("veXeThu2",veThuHai);
	   }
	   return "site/datve/step4";
   }
   @RequestMapping(value = "userBookedTickets/{userId}.htm",method = RequestMethod.POST)
   public String luuVePage(@RequestParam("hinhThucThanhToan") String hinhThuc, HttpServletRequest request,ModelMap model,@PathVariable("userId") Integer userId) {
		
		/////////////// Nếu là vé 1 chiều
		String loaiVe = (String) request.getSession().getAttribute("loaiVe");
		if (loaiVe.equals("motchieu")) {
			Ve_Xe veXe = (Ve_Xe)request.getSession().getAttribute("veXeChinhThuc");
			// set hinh thuc thanh toan
			veXe.setHinhThucThanhToan(hinhThuc);
			// set ngay luu ve để lưu vào cơ sở dữ liệu
			LocalDateTime dateObj = LocalDateTime.now();
			DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatedDate = dateObj.format(formatObj);
			veXe.setNgayLap(formatedDate);
			veXe.setTrangThai("Chờ thanh toán");
//			veXeService.luuVe(veXe);
			Integer check=this.saveve(veXe);
			if(check==1) {
				model.addAttribute("message","dat ve thanh cong");
				System.out.print(check);
			}else {
				System.out.print(check);
			}
			request.getSession().removeAttribute("veXeChinhThuc");
			request.getSession().removeAttribute("loaiVe");
			
		} else {	//////////// Nếu là vé khứ hồi
			
			Ve_Xe veXeThu1 = (Ve_Xe)request.getSession().getAttribute("veXeChinhThuc1");
			Ve_Xe veXeThu2 = (Ve_Xe)request.getSession().getAttribute("veXeChinhThuc");
			// set hinh thuc thanh toan
			veXeThu1.setHinhThucThanhToan(hinhThuc);
			veXeThu2.setHinhThucThanhToan(hinhThuc);
			// set ngay luu ve để lưu vào cơ sở dữ liệu
			LocalDateTime dateObj = LocalDateTime.now();
			DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatedDate = dateObj.format(formatObj);
			veXeThu1.setNgayLap(formatedDate);
			veXeThu2.setNgayLap(formatedDate);
			veXeThu1.setTrangThai("Chờ thanh toán");
			veXeThu2.setTrangThai("Chờ thanh toán");
//			veXeService.luuVe(veXeThu1);
//			veXeService.luuVe(veXeThu2);
			Integer check1=this.saveve(veXeThu1);
			Integer check2=this.saveve(veXeThu2);
				System.out.print(check1);
				System.out.print(check2);
			request.getSession().removeAttribute("veXeChinhThuc");
			request.getSession().removeAttribute("veXeChinhThuc1");
			request.getSession().removeAttribute("loaiVe");
		}
		List<Ve_Xe> list=this.getve(userId);
    	System.out.println(list);
    	model.addAttribute("listve",list);
		return "site/user/userBookedTickets";
	}
   public List<Ve_Xe> getve(Integer userid){
   	Session session=factory.getCurrentSession();
   	String hql="from Ve_Xe v where v.idKhachHang.userId =:userid";
   	Query query=session.createQuery(hql);
   	query.setParameter("userid", userid);
   	List<Ve_Xe> list=query.list();
   	return list;
   }
   public Integer saveve(Ve_Xe ve) {
	   Session session=factory.openSession();
	   Transaction t=session.beginTransaction();
	   try {
		  session.save(ve);
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
   public User getkh(Integer userId) {
	   Session session=factory.getCurrentSession();
	   String hql="From User where userId =:userId";
	   Query query=session.createQuery(hql);
	   query.setParameter("userId", userId);
	   User list=(User)query.list().get(0);
	   return list;
   }
    
   public Chuyen_Xe getChuyenXe(Integer chuyenxe){
	   Session session=factory.getCurrentSession();
	   String hql="from Chuyen_Xe where maChuyen =:chuyenxe ";
	   Query query=session.createQuery(hql);
	   query.setParameter("chuyenxe", chuyenxe);
	   Chuyen_Xe list=(Chuyen_Xe) query.list().get(0);
	   return list;
   }
   public List<Chuyen_Xe> getChuyenXeThoaMan(String ngayDi,int idTuyenXe){
	   Session session=factory.getCurrentSession();
	   Query query=session.createSQLQuery("EXEC Lay_Chuyen_Xe_Trong_Ngay :Ngay,:idTuyenXe").addEntity(Chuyen_Xe.class)
				 .setParameter("Ngay", ngayDi)
				 .setParameter("idTuyenXe", idTuyenXe);
	   List<Chuyen_Xe> list=query.list();
	   return list;
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
}
