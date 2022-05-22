package poly.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.Entity.Ghe;
import ptithcm.Entity.Ve_Xe;

import ptithcm.Entity.Chuyen_Xe;
import ptithcm.Entity.Tuyen_Xe;

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
   @RequestMapping("step3/{userId}.htm")
   public String step3(ModelMap model,@PathVariable("userId") Integer userId) {
	   return "site/datve/step3";
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
