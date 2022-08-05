let message = document.getElementById("result").innerHTML

if(message === 'Invalid Username or password') {
    document.getElementById('UserName').style.borderBottomColor = 'red'
    document.getElementById('Password').style.borderBottomColor = 'red'
}