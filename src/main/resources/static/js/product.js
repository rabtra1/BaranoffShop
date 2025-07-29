
const btnPlus = document.querySelector('#plus');
const btnMinus = document.querySelector('#minus');
const input = document.querySelector('#input')

let num = 1;

btnPlus.addEventListener('click', function(){
    let attribute = input.getAttribute("max");
    if (num >= attribute) {
        input.value = attribute;
    } else {
        num += 1
        input.value = num
    }
})

btnMinus.addEventListener('click', function(){
    if (num <= 1){
        input.value = 1
    }else {
        num -= 1
        input.value = num
    }
})