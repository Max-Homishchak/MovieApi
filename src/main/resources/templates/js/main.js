let message = document.getElementById("result").innerHTML

switch (message) {
    case 'You have already rated this movie':
        document.getElementById('MovieName').style.borderBottomColor = 'red'
        document.getElementById('MovieRate').style.borderBottomColor = 'red'
        document.getElementById("result").style.color = 'red'
        break;
    case 'You have successfully rated this movie':
        document.getElementById('MovieName').style.borderBottomColor = '#AFE1AF'
        document.getElementById('MovieRate').style.borderBottomColor = '#AFE1AF'
        document.getElementById("result").style.color = '#AFE1AF'
        break;
}