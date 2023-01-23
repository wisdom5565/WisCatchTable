function test() {
    if ($('.display').css('display') == 'block') {
        $('.display').css('display', 'none');
    } else {
        $('.display').css('display', 'block');
    }
}

function test1() {
    if ($('.display1').css('display') == 'block') {
        $('.display1').css('display', 'none');
    } else {
        $('.display1').css('display', 'block');
    }
}
 
// const toggleButton = document.querySelectorAll('#toggleButton');
// toggleButton.addEventListener('click', () => {
//   const container = document.querySelector('#container');
//   console.log(container);
//   if(container.getAttribute('class', 'remove')) {
//     container.removeAttribute('class', 'remove');
//   } else {
//     container.setAttribute('class', 'remove');
//   }
// })




// {/* <button id="toggleButton">Toggle</button>
// <div id="container">
//   <h2 id="title">This is Display</h2>
// </div> */}

// const toggleButton = document.querySelector('#toggleButton');
// toggleButton.addEventListener('click', () => {
//   const container = document.querySelector('#container');
//   console.log(container);
//   if(container.getAttribute('class', 'test1')) {
//     container.removeAttribute('class', 'test1');
//   } else {
//     container.setAttribute('class', 'test1');
//   }
// })

// const conclusiones = document.querySelectorAll("#togglebutton");
// conclusiones.forEach((conclusion)=>{
//     conclusion.addEventListener("click",() => {
//         conclusiones.forEach((e) => {
//             const container = document.querySelector('#container');
//             e.classList.remove("on");
//             // e.setAttribute("aria-selected","false");
//         })
//     conclusion.classList.add("on");
//     // conclusion.setAttribute("aria-selected","true");
//     })
// });  

// const toggleButton = document.querySelectorAll('#toggleButton');
// toggleButton.forEach((a) => {
    
//     a.addEventListener('click', () => {
//         const container = document.querySelector('#container');
//         console.log(container);
//         if(container.getAttribute('class', 'remove')) {
//           container.removeAttribute('class', 'remove');
//           container.setAttribute("aria-selected","false");
//         } else {
//           container.setAttribute('class', 'remove');
//           container.setAttribute("aria-selected","true");
//         }
//     })
    
// })

const items = document.querySelectorAll("#toggleButton");

    items.forEach((item)=>{

        item.addEventListener('click',()=>{

            const ch=item.childNodes;
            // console.log(ch);

            if(ch[3].getAttribute('class', 'remove')){
               ch[3].removeAttribute('class', 'remove');
                // 숫자 ++1
            }else{
                ch[3].setAttribute('class', 'remove');
            }
            
        })
    })

// const currentNode = document.querySelector("#toggleButton");

// console.log(currentNode.nextElementSibling);

// var currentNode = document.querySelector("#toggleButton-3");
// var nextNode = currentNode.nextElementSibling;

// while(nextNode) {
//   console.log(nextNode);
//   nextNode = nextNode.nextElementSibling;
// }

// const $btn = document.getElementById('toggleButton');

// let count = 0;

// function addListener() {
//     if ($btn.clickHandler) {
//         $btn.removeEventListener('click', $btn.clickHandler);
//     }

//     $btn.clickHandler = () => {
//         const container = document.querySelector('#container');
//         console.log(container);
//         if(container.getAttribute('class', 'remove')) {
//             container.removeAttribute('class', 'remove');
//             container.setAttribute("aria-selected","false");
//         } else {
//             container.setAttribute('class', 'remove');
//             container.setAttribute("aria-selected","true");
//         }
//     };

//     $btn.addEventListener('click', $btn.clickHandler);

// }

