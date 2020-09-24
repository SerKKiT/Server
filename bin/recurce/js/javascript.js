var inpName = document.getElementById('inpName');
var inpFirstName = document.getElementById('inpFirstName');
var inpLogin = document.getElementById('inpLogin');
var inpPasword = document.getElementById('password-input');
var inpTwoPasword = document.getElementById('password-input2');
var inpEmail = document.getElementById('inpEmail');

  inpName.onfocus = function (event) {
    document.getElementById('inpTextName').className = "textinput2";
    document.getElementById('inpTextName').parentNode.style.margin = "0px auto 10px auto";
  };
  inpName.onblur = function (event) {
    if(inpName.value==""){
    document.getElementById('inpTextName').className = "textinput";
    document.getElementById('inpTextName').parentNode.style.margin = "35px auto 10px auto";
  }
  };
  inpFirstName.onfocus = function (event) {
    document.getElementById('inpTextFirstName').className = "textinput2";
    document.getElementById('inpTextFirstName').parentNode.style.margin = "0px auto 10px auto";
  };
  inpFirstName.onblur = function (event) {
    if(inpFirstName.value==""){
    document.getElementById('inpTextFirstName').className = "textinput";
    document.getElementById('inpTextFirstName').parentNode.style.margin = "35px auto 10px auto";
  }
  };
  inpLogin.onfocus = function (event) {
    document.getElementById('inpTextLogin').className = "textinput2";
    document.getElementById('inpTextLogin').parentNode.style.margin = "0px auto 10px auto";
  };
  inpLogin.onblur = function (event) {
    if(inpLogin.value==""){
    document.getElementById('inpTextLogin').className = "textinput";
    document.getElementById('inpTextLogin').parentNode.style.margin = "35px auto 10px auto";
  }
  };
  inpPasword.onfocus = function (event) {
    document.getElementById('inpTextPasword').className = "textinput2";
    document.getElementById('inpTextPasword').parentNode.style.margin = "0px auto 10px auto";
  };
  inpPasword.onblur = function (event) {
    if(inpPasword.value==""){
    document.getElementById('inpTextPasword').className = "textinput";
    document.getElementById('inpTextPasword').parentNode.style.margin = "35px auto 10px auto";
  }
  };
  inpTwoPasword.onfocus = function (event) {
    document.getElementById('inpTextTwoPasword').className = "textinput2";
    document.getElementById('inpTextTwoPasword').parentNode.style.margin = "0px auto 10px auto";
  };
  inpTwoPasword.onblur = function (event) {
    if(inpTwoPasword.value==""){
    document.getElementById('inpTextTwoPasword').className = "textinput";
    document.getElementById('inpTextTwoPasword').parentNode.style.margin = "35px auto 10px auto";
  }
  };
  inpEmail.onfocus = function (event) {
    document.getElementById('inpTextEmail').className = "textinput2";
    document.getElementById('inpTextEmail').parentNode.style.margin = "0px auto 5px auto";
  };
  inpEmail.onblur = function (event) {
    if(inpEmail.value==""){
    document.getElementById('inpTextEmail').className = "textinput";
    document.getElementById('inpTextEmail').parentNode.style.margin = "35px auto 5px auto";
  }
  };



function show_hide_password(target){
  var input = document.getElementById('password-input');
  var input2 = document.getElementById('password-input2');
  if (input.getAttribute('type') == 'password') {
    target.classList.add('view');
    input.setAttribute('type', 'text');
    input2.setAttribute('type', 'text');
  } else {
    target.classList.remove('view');
    input.setAttribute('type', 'password');
    input2.setAttribute('type', 'password');
  }
  return false;
}

function checkPolitics(){
  if(!document.getElementById('Politics').checked){
    document.getElementById('Registration').setAttribute('disabled', true);
  }else{
    document.getElementById('Registration').removeAttribute('disabled');
  }
}
window.onresize= function(){
     if(window.innerWidth < 1050){
       document.getElementById("preview").style.display = "none";
       document.getElementById("reg").style.left = "0%";
       document.getElementById("reg").style.width = "100%";
       document.getElementById("reg").setAttribute("align","center");
     }else {
       document.getElementById("preview").style.display = "inline-block";
       document.getElementById("reg").style.left = "50%";
       document.getElementById("reg").style.width = "50%";
       document.getElementById("reg").setAttribute("align","left");
     }
};
