let message = document.getElementById("result").innerHTML

switch (message) {
    case 'Password is too short':
        document.getElementById('UserPassword').style.borderBottomColor = 'red'
        break;
    case 'This username is already used':
        document.getElementById('UserName').style.borderBottomColor = 'red'
        break;
    case 'This email is already used':
    case 'Email is not valid':
        document.getElementById('UserEmail').style.borderBottomColor = 'red'
        break;
    case 'This email and username are already used':
        document.getElementById('UserName').style.borderBottomColor = 'red'
        document.getElementById('UserEmail').style.borderBottomColor = 'red'
        break;
}