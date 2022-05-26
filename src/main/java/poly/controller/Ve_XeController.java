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
import ptithcm.Entity.Dia_Diem;
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
					List<Ghe> gheList = veXe.getGheList(); // Ä‘Ã¢y lÃ  list cÃ¡c gháº¿ Ä‘Ã£ Ä‘áº·t tÆ°Æ¡ng á»©ng vá»›i má»—i vÃ©
					listCacGheDaDatTuongUngVoiMoiChuyenXe.addAll(gheList);
				}
			}
			hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe.put(chuyenXe.getMaChuyen(),
					listCacGheDaDatTuongUngVoiMoiChuyenXe); // Xá»­ lÃ­ sá»‘ chá»— cÃ²n láº¡i cá»§a má»—i chuyáº¿n xe
		}

		model.addAttribute("hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe",
				hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe);
	   return "site/datve/step2";
   }
   @RequestMapping("step3")
   public String step3(ModelMap model) {
	   return "site/datve/step3";
   }
   @RequestMapping(value = "step2KhuHoi/{userId}.htm", method = RequestMethod.GET)
   public String step2Khuhoi(ModelMap model,@PathVariable("userId") Integer userId,HttpServletRequest request, @RequestParam("ngayVe") String ngayVe) {
	   Ve_Xe veThuNhat = null;
	   User tempUser=this.getkh(userId);
	   if(tempUser.getIdTaiKhoan().getIdRole().getAuthority().equals("ROLE_USER")) {
		   veThuNhat = (Ve_Xe) request.getSession().getAttribute("veXeChinhThuc1");
	   }else {
		  veThuNhat = (Ve_Xe) request.getSession().getAttribute("veXeTam1");
	   }
	   Dia_Diem diaDiemDi = veThuNhat.getIdChuyenXe().getMaTuyen().getDiaDiemDen();
	   Dia_Diem diaDiemDen = veThuNhat.getIdChuyenXe().getMaTuyen().getDiaDiemDi();
	   Tuyen_Xe tuyenXeCanTim=this.gettuyenxe(diaDiemDi.getIdDiaDiem(), diaDiemDen.getIdDiaDiem());
	   model.addAttribute("tuyenXeCanTim", tuyenXeCanTim);
	   model.addAttribute("ngayDi", ngayVe);
	   List<Chuyen_Xe> chuyenXeCanTim=this.getChuyenXeThoaMan(ngayVe, tuyenXeCanTim.getIdTuyen());
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
				// làm chỗ này là lấy những vé mà không bị hủy
				if (!veXe.getTrangThai().equals("Đã hủy")) {// vì đã hủy là các ghế của vé đó vẫn đang chưa được đặt
					List<Ghe> gheList = veXe.getGheList(); // đây là list các ghế đã đặt tương ứng với mỗi vé
					listCacGheDaDatTuongUngVoiMoiChuyenXe.addAll(gheList);
				}

			}
			hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe.put(chuyenXe.getMaChuyen(),
					listCacGheDaDatTuongUngVoiMoiChuyenXe);
			// Xử lí số chỗ còn lại của mỗi chuyến xe
		}

		model.addAttribute("hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe",
				hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe);
	   return "site/datve/step2khuhoi";
   }
   @RequestMapping(value = "step2KhuHoi", method = RequestMethod.GET)
   public String step2Khuhoinv(ModelMap model,HttpServletRequest request, @RequestParam("ngayVe") String ngayVe) {
	   Ve_Xe veThuNhat = null;
//	   User tempUser=this.getkh(userId);
	 
		  veThuNhat = (Ve_Xe) request.getSession().getAttribute("veXeTam1");
	   
	   Dia_Diem diaDiemDi = veThuNhat.getIdChuyenXe().getMaTuyen().getDiaDiemDen();
	   Dia_Diem diaDiemDen = veThuNhat.getIdChuyenXe().getMaTuyen().getDiaDiemDi();
	   Tuyen_Xe tuyenXeCanTim=this.gettuyenxe(diaDiemDi.getIdDiaDiem(), diaDiemDen.getIdDiaDiem());
	   model.addAttribute("tuyenXeCanTim", tuyenXeCanTim);
	   model.addAttribute("ngayDi", ngayVe);
	   List<Chuyen_Xe> chuyenXeCanTim=this.getChuyenXeThoaMan(ngayVe, tuyenXeCanTim.getIdTuyen());
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
				// làm chỗ này là lấy những vé mà không bị hủy
				if (!veXe.getTrangThai().equals("Đã hủy")) {// vì đã hủy là các ghế của vé đó vẫn đang chưa được đặt
					List<Ghe> gheList = veXe.getGheList(); // đây là list các ghế đã đặt tương ứng với mỗi vé
					listCacGheDaDatTuongUngVoiMoiChuyenXe.addAll(gheList);
				}

			}
			hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe.put(chuyenXe.getMaChuyen(),
					listCacGheDaDatTuongUngVoiMoiChuyenXe);
			// Xử lí số chỗ còn lại của mỗi chuyến xe
		}

		model.addAttribute("hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe",
				hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe);
	   return "site/datve/step2khuhoi";
   }
   @RequestMapping(value = "step2KhuHoi/{userId}.htm",method = RequestMethod.POST)
   public String step2khuhoi(@RequestParam("ngayVe") String ngayVe, @RequestParam("chuyenXe") int idChuyenXe,
			@RequestParam("gheCheckBox") List<Ghe> gheList, ModelMap model, HttpServletRequest request,@PathVariable("userId") Integer userId) {
	   Ve_Xe ve = new Ve_Xe();
	   User tempUser=this.getkh(userId);
		// khúc này là xác minh xem người hiện tại đang đặt vé là người dùng hay nhân
		// viên
		// Nếu là người dùng đã đăng kí rồi thì không cần lưu người dùng vào db vì đã có
		// sẵn
		if (tempUser.getIdTaiKhoan().getIdRole().getAuthority().equals("ROLE_USER")) {
			User tempUser2 = this.getkh(userId);
			ve.setIdKhachHang(tempUser2);
		}

		Chuyen_Xe tempChuyenXe = this.getChuyenXe(idChuyenXe);
		ve.setIdChuyenXe(tempChuyenXe);
		ve.setGheList(gheList);
		Xe tempXe = tempChuyenXe.getMaXe();
		long tongTien = (tempChuyenXe.getMaXe().getMaLoaiXe().getTienVeMoiCho()) * gheList.size();
		ve.setTongTien(tongTien);

		if (tempUser.getIdTaiKhoan().getIdRole().getAuthority().equals("ROLE_USER")) {

			request.getSession().setAttribute("veXeChinhThuc1", ve);
		} else {
			request.getSession().setAttribute("veXeTam1", ve);
		}
	   return "redirect:/site/step2KhuHoi/" +userId + ".htm?ngayVe=" + ngayVe;
   }
   @RequestMapping(value = "step2KhuHoi",method = RequestMethod.POST)
   public String step2khuhoinv(@RequestParam("ngayVe") String ngayVe, @RequestParam("chuyenXe") int idChuyenXe,
			@RequestParam("gheCheckBox") List<Ghe> gheList, ModelMap model, HttpServletRequest request) {
	   Ve_Xe ve = new Ve_Xe();
//	   User tempUser=this.getkh(userId);
		// khúc này là xác minh xem người hiện tại đang đặt vé là người dùng hay nhân
		// viên
		// Nếu là người dùng đã đăng kí rồi thì không cần lưu người dùng vào db vì đã có
		// sẵn
//		if (tempUser.getIdTaiKhoan().getIdRole().getAuthority().equals("ROLE_USER")) {
//			User tempUser2 = this.getkh(userId);
//			ve.setIdKhachHang(tempUser2);
//		}

		Chuyen_Xe tempChuyenXe = this.getChuyenXe(idChuyenXe);
		ve.setIdChuyenXe(tempChuyenXe);
		ve.setGheList(gheList);
		Xe tempXe = tempChuyenXe.getMaXe();
		long tongTien = (tempChuyenXe.getMaXe().getMaLoaiXe().getTienVeMoiCho()) * gheList.size();
		ve.setTongTien(tongTien);

		
			request.getSession().setAttribute("veXeTam1", ve);
		
	   return "redirect:/site/step2KhuHoi"+ ".htm?ngayVe=" + ngayVe;
   }
   @RequestMapping(value = "step3/{userId}.htm",method = RequestMethod.POST)
   public String step3(ModelMap model,@PathVariable("userId") Integer userId,@RequestParam("chuyenXe") int idChuyenXe,
			@RequestParam("gheCheckBox") List<Ghe> gheList,HttpServletRequest request) {
	   String loaiVe = (String) request.getSession().getAttribute("loaiVe");
		// -> Náº¿u loáº¡i vÃ© lÃ  má»™t chiá»�u thÃ¬ vÃ© bÃªn dÆ°á»›i lÃ  vÃ© thá»© nháº¥t
		// ->náº¿u loáº¡i vÃ© lÃ  khá»© há»“i thÃ¬ vÃ© nÃ y lÃ  vÃ© thá»© vá»�, vÃ© Ä‘i cÃ³ tÃªn lÃ 
		// veXeChinhThuc1 or veXeTam1 trong session
		// Để cho đỡ rối thì nên lặp code 1 tí

		if (loaiVe.equals("motchieu")) {
			Ve_Xe ve = new Ve_Xe();

			// khÃºc nÃ y lÃ  xÃ¡c minh xem ngÆ°á»�i hiá»‡n táº¡i Ä‘ang Ä‘áº·t vÃ© lÃ  ngÆ°á»�i dÃ¹ng hay nhÃ¢n
			// viÃªn
			// Náº¿u lÃ  ngÆ°á»�i dÃ¹ng Ä‘Ã£ Ä‘Äƒng kÃ­ rá»“i thÃ¬ khÃ´ng cáº§n lÆ°u ngÆ°á»�i dÃ¹ng vÃ o db vÃ¬ Ä‘Ã£ cÃ³
			// sáºµn
			
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
		// nÃ y náº¿u loáº¡i vÃ© lÃ  khá»© há»“i
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
				// láº¥y vÃ© thá»© nháº¥t ra setIdKhachHang
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
   @RequestMapping(value = "step3",method = RequestMethod.POST)
   public String step3nv(ModelMap model,@RequestParam("chuyenXe") int idChuyenXe,
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
          
        
			Chuyen_Xe tempChuyenXe = this.getChuyenXe(idChuyenXe);
			ve.setIdChuyenXe(tempChuyenXe);
			ve.setGheList(gheList);
			Xe tempXe = tempChuyenXe.getMaXe();
			long tongTien = (tempChuyenXe.getMaXe().getMaLoaiXe().getTienVeMoiCho()) * gheList.size();
			ve.setTongTien(tongTien);

			
				request.getSession().setAttribute("veXeTam", ve);
		}
		// này nếu loại vé là khứ hồi
		else {
			Ve_Xe veThuHai = new Ve_Xe();

			// khúc này là xác minh xem người hiện tại đang đặt vé là người dùng hay nhân
			// viên
			// Nếu là người dùng đã đăng kí rồi thì không cần lưu người dùng vào db vì đã có
			// sẵn
//			if (tempUser.getIdTaiKhoan().getIdRole().getAuthority().equals("ROLE_USER")) {
//
//				veThuHai.setIdKhachHang(tempUser);
//				// lấy vé thứ nhất ra setIdKhachHang
//				Ve_Xe veThuNhat = (Ve_Xe) request.getSession().getAttribute("veXeChinhThuc1");
//				veThuNhat.setIdKhachHang(tempUser);
//				request.getSession().setAttribute("veXeChinhThuc1", veThuNhat);
//
//			}
			Chuyen_Xe tempChuyenXe = this.getChuyenXe(idChuyenXe);
			veThuHai.setIdChuyenXe(tempChuyenXe);
			veThuHai.setGheList(gheList);
			Xe tempXe = tempChuyenXe.getMaXe();
			long tongTien = (tempChuyenXe.getMaXe().getMaLoaiXe().getTienVeMoiCho()) * gheList.size();
			veThuHai.setTongTien(tongTien);
		    request.getSession().setAttribute("veXeTam", veThuHai);
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
   @RequestMapping(value ="step4",method = RequestMethod.POST)
   public String step4post(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "hoTen", required = false) String hoTen,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "cmnd", required = false) String cmnd,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "doiThongTin", required = false) String doiThongTin) {
	   
	   Ve_Xe veXeDangDat = (Ve_Xe) request.getSession().getAttribute("veXeTam");
		// coi xem phonenumber đặt vào đã có user trong db chưa, nếu có thì hiện bảng
		// xem có muốn
		// thay đổi thông tin user sang cái mời vừa nhập không,
		User tempUser = this.getUserFromUsername(phoneNumber);
		
		//System.err.println(tempUser);
		if (tempUser != null && doiThongTin == null) {
			User newUser = new User();
			newUser.setUserId(tempUser.getUserId());
			newUser.setHoTen(hoTen);
			newUser.setPhoneNumber(phoneNumber);
			newUser.setEmail(email);
			newUser.setCmnd(cmnd);
			newUser.setDiaChi(diaChi);
			request.getSession().setAttribute("tempUser", tempUser);
			request.getSession().setAttribute("newUser", newUser);
			return "redirect:/site/step3.htm?daTonTai=true";
		}
		if (tempUser != null && doiThongTin.equals("true")) {
			User tempUser1 = (User) request.getSession().getAttribute("newUser");
			tempUser.setHoTen(tempUser1.getHoTen());
			tempUser.setEmail(tempUser1.getEmail());
			tempUser.setCmnd(tempUser1.getCmnd());
			tempUser.setDiaChi(tempUser1.getDiaChi());
			int id = tempUser.getUserId();
			this.updateuser(tempUser);
//			tempUser = userService.getUser(id);
			tempUser=this.getkh(id);
			
		}
		if (tempUser == null) {
			tempUser = new User();
			tempUser.setHoTen(hoTen);
			tempUser.setPhoneNumber(phoneNumber);
			tempUser.setEmail(email);
			tempUser.setCmnd(cmnd);
			tempUser.setDiaChi(diaChi);
		}

		veXeDangDat.setIdKhachHang(tempUser);
		request.getSession().setAttribute("veXeChinhThuc", veXeDangDat);
		// nếu là vé khứ hồi thì lấy vé thứ nhất ra set thông tin như trên, nằm trong
		// veXeTam1
		String loaiVe = (String) request.getSession().getAttribute("loaiVe");
		if (loaiVe.equals("khuhoi")) {
			Ve_Xe veThuNhat = (Ve_Xe) request.getSession().getAttribute("veXeTam1");
			veThuNhat.setIdKhachHang(tempUser);
			request.getSession().setAttribute("veXeChinhThuc1", veThuNhat);
		}
		return "redirect:/site/step4.htm";
   }
   @RequestMapping("veChuaThanhToan")
   public String vechuathanhtoan(@RequestParam("hinhThucThanhToan") String hinhThuc, HttpServletRequest request,ModelMap model) {
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
			// xem user đã từng đăng kí offline chưa, nều rồi thì chỉ lưu vé, nếu chưa thì mới save User mới
//			User tempUser = userService.getUserFromUsername(veXe.getIdKhachHang().getPhoneNumber());
			User tempUser=this.getUserFromUsername(veXe.getIdKhachHang().getPhoneNumber());
			if (tempUser == null) {
//				userService.saveUser(veXe.getIdKhachHang());
				this.saveuser(veXe.getIdKhachHang());
			} else {
				veXe.setIdKhachHang(tempUser);
			}
//			veXeService.luuVe(veXe);
			this.saveve(veXe);
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
//			User tempUser = userService.getUserFromUsername(veXeThu1.getIdKhachHang().getPhoneNumber());
			User tempUser=this.getUserFromUsername(veXeThu1.getIdKhachHang().getPhoneNumber());
			if (tempUser == null) {
//				userService.saveUser(veXeThu1.getIdKhachHang());
				this.saveuser(veXeThu1.getIdKhachHang());
			} else {
				//veXeThu2.setIdKhachHang(tempUser);
			}
			
			
//			veXeService.luuVe(veXeThu1);
//			veXeService.luuVe(veXeThu2);
			this.saveve(veXeThu1);
			this.saveve(veXeThu2);
			request.getSession().removeAttribute("veXeChinhThuc");
			request.getSession().removeAttribute("veXeChinhThuc1");
			request.getSession().removeAttribute("loaiVe");
		}
		List<Ve_Xe> list=this.getves();
		   System.out.println(list);
		   model.addAttribute("trang_thai_ve",list);
			return "site/nhanvien/veChuaThanhToanList";
   }
   
   @RequestMapping(value = "userBookedTickets/{userId}.htm",method = RequestMethod.POST)
   public String luuVePage(@RequestParam("hinhThucThanhToan") String hinhThuc, HttpServletRequest request,ModelMap model,@PathVariable("userId") Integer userId) {
		
		/////////////// Náº¿u lÃ  vÃ© 1 chiá»�u
		String loaiVe = (String) request.getSession().getAttribute("loaiVe");
		if (loaiVe.equals("motchieu")) {
			Ve_Xe veXe = (Ve_Xe)request.getSession().getAttribute("veXeChinhThuc");
			// set hinh thuc thanh toan
			veXe.setHinhThucThanhToan(hinhThuc);
			// set ngay luu ve Ä‘á»ƒ lÆ°u vÃ o cÆ¡ sá»Ÿ dá»¯ liá»‡u
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
			
		} else {
			Ve_Xe veXeThu1 = (Ve_Xe)request.getSession().getAttribute("veXeChinhThuc1");
			Ve_Xe veXeThu2 = (Ve_Xe)request.getSession().getAttribute("veXeChinhThuc");
			// set hinh thuc thanh toan
			veXeThu1.setHinhThucThanhToan(hinhThuc);
			veXeThu2.setHinhThucThanhToan(hinhThuc);
			// set ngay luu ve Ä‘á»ƒ lÆ°u vÃ o cÆ¡ sá»Ÿ dá»¯ liá»‡u
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
		model.addAttribute("listve",this.getve1(userId));
		return "site/user/userBookedTickets";
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
   public Integer updateuser(User user) {
	   Session session=factory.openSession();
	   Transaction t=session.beginTransaction();
	   try {
		  session.update(user);
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
   public Integer saveuser(User user) {
	   Session session=factory.openSession();
	   Transaction t=session.beginTransaction();
	   try {
		session.save(user);
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
   public User getUserFromUsername(String username) {
	   Session session=factory.getCurrentSession();
	   String hql = "FROM User U WHERE U.phoneNumber = :username";
	   Query query=session.createQuery(hql);
	   query.setParameter("username", username);
	   User list=(User) query.list().get(0);
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
   public Ve_Xe getve(Integer idve){
  	 Session session=factory.getCurrentSession();
  	 String hql="From Ve_Xe where idVe =:idve";
  	 Query query=session.createQuery(hql);
  	 query.setParameter("idve", idve);
  	 Ve_Xe list =(Ve_Xe) query.list().get(0);
  	 return list;
   }
   public List<Ve_Xe> getve1(Integer userid){
  	 Session session=factory.getCurrentSession();
   	String hql="from Ve_Xe v where v.idKhachHang.userId =:userid";
   	Query query=session.createQuery(hql);
   	query.setParameter("userid", userid);
   	List<Ve_Xe> list=query.list();
   	return list;
   }
}
