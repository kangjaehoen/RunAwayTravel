let myPage = document.getElementById("myPage");
let myPageList = document.getElementById("myPageList");


myPage.onclick = function() {
    let rect = myPage.getBoundingClientRect();


    myPageList.style.top = rect.bottom + "px";
    myPageList.style.left = rect.left + "px";
    if (!id ||id === "null") {
        if (myPageList.style.display === "none" || myPageList.style.display === "") {
            myPageList.style.display = "block";
        } else {
            myPageList.style.display = "none";
        }
    }else{
         if (myPageList2.style.display === "none" || myPageList2.style.display === "") {
                    myPageList2.style.display = "block";
                } else {
                    myPageList2.style.display = "none";
                }

    }
}