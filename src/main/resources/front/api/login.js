function loginApi(data) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data
    })
  }

function loginoutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}

  
function GetValidationCodeApi(data) {
  return $axios({
    'url': '/user/validatecode',
    'method' : 'get',
   	params:{...data}
  })
}