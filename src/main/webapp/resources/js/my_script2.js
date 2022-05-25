/*const suf= document.getElementById('signup')

suf.addEventListener('onsubmit',submitHandler())
*/
//const hte = document.getElementById('hte')
function inputHandler(x){
		const diverror= document.getElementById(x)
		diverror.innerText="";
}
function alSignUp(){
	
}
(function () {
		  const thanhCongInput = document.getElementById("thanhcong");
		  if(thanhCongInput.value!=null){
			if (thanhCongInput.value === "thanhcong") {
			swal({
			  title: "Good job!",
			  text: "Dang ki thanh cong!",
			  icon: "success",
			  button: "Aw` yess!",
			}).then(()=>{
				window.location.href = "site/sign_in.htm";
			});
			}else if(thanhCongInput.value === "trungsdt"){
				swal({
				  title: "Opps",
				  text: "So dien thoai bi trung !",
				  icon: "error",
				  button: "Nooooooooooo!",
				})();
				
				
			}else if(thanhCongInput.value === "thatbai"){
				swal({
				  title: "Opps",
				  text: "Dang ki that bai !",
				  icon: "error",
				  button: "Nooooooooooo!",
				})();
			}
		}
})()
swal1({
			  title: "Good job!",
			  text: "aaaaaa!",
			  icon: "success",
			  button: "Aww yiss!",
});

function checkpass(){
	const pass1= document.getElementById(fgpass1)
	const pass2= document.getElementById(fgpass2)
	const mess= document.getElementById(messcfpass)
	if(pass1!=pass2) {
		mess.innerText="Mat khau nhap lai khong dung";
		return false;
	}
}