// 检查是否登录，没登录就t出去
function isLogin(){
    $.ajax({
        url:'user/isLogin',
        type:'POST',
        dataType:'JSON',
        success: function(res){
            if(!res){
                window.location.href="index.html";
            }else{
                $('#dropdownMenuLink').html($.cookie("username"));
            }
        }
    })
}
function logOut(){
    $.get({
        url:'user/logOut',
        success:function(){
            window.location.href="index.html";
        }
    })
}
// 上传组件
function select(e){
    $('#selected').val(e.dataset.id);
    // console.log(e.dataset.id)
    // console.log($('#selected').val())
    $('#inputGroupFile').click();
    $('#selectModel').hide();
}
function hideSelect(){
    $('#selectModel').hide();
}

function deletePhoto(e) {
    var r = confirm("是否确认删除？");
    if (r) {
        // console.log("delete：", e.dataset.id)
        var id = e.dataset.id;
        var pgindex = e.dataset.pgindex;
        var imgindex = e.dataset.imgindex;
        $.ajax({
            url: "photo/deletePhoto",
            type: "POST",
            dataType: "json",
            data: {
                id: e.dataset.id
            },
            success: function (res) {
                vtoastDelete.successful = res;
                if (res) {
                    // 在vue中删除第imgindex个元素
                    vphoto.photosData[pgindex].photos.splice(imgindex, 1);
                    // 如果所删照片的组没有其他照片，则删除该组
                    if (vphoto.photosData[pgindex].photos.length == 0) {
                        vphoto.photosData.splice(pgindex, 1);
                    }
                }
                $('#toastDelete').toast('show');

            }
        })
    }

}
function deleteAlbum(e) {
    var r = confirm("是否确认删除？");
    if (r) {
        // console.log("delete：", e.dataset.id)
        var id = e.dataset.id;
        var albumindex = e.dataset.albumindex;

        $.ajax({
            url: "album/deleteAlbum",
            type: "POST",
            dataType: "json",
            data: {
                id: id
            },
            success: function (res) {
                vtoastDelete.successful = res;
                if (res) {
                    // 在vue中删除第imgindex个元素
                    valbum.albums.splice(albumindex, 1);
                    vAlbumListTab.albums.splice(albumindex, 1);
                }
                $('#toastDelete').toast('show');

            }
        })
    }

}
function changeMain(){
    var contents = document.querySelectorAll('.content');
    var hash = window.location.hash;
    for(var i=0;i<contents.length;i++){
        $(contents[i]).hide();
    }
    // console.log("hash",window.location.hash)
   if(window.location.hash!=''){
    $(window.location.hash).show();
   }else{
    //    默认显示页面
       $('#photosGroup').show();
   }
}
function addhashListener(){
    if( ('onhashchange' in window) && ((typeof document.documentMode==='undefined') || document.documentMode==8)) {
        // 浏览器支持onhashchange事件
        window.onhashchange = changeMain;
    } else {
        // 不支持则用定时器检测的办法
        setInterval(function() {
            // 检测hash值或其中某一段是否更改的函数， 在低版本的iE浏览器中通过window.location.hash取出的指和其它的浏览器不同，要注意
            var ischanged = isHashChanged();
            if(ischanged) {
                changeMain();
            }
        }, 150);
    }
}
function getPhotos(){
    $.ajax({
        url: "photo/getPhotos",
        type: "POST",
        dataType: "json",
        data: {
            id: $.cookie("id")
        },
        success: function (res) {
            vphoto.photosData = res;
        }
    })
}
function getAlbums(){
    $.ajax({
        url: "album/getAlbums",
        type: "POST",
        dataType: "json",
        data: {
        },
        success: function (res) {
            vAlbumListTab.albums = res;
            valbum.albums = res;
        }
    })
}
function hideAddAlbumModel(){
    $('#albumName').val('');
    $('#addAlbumModel').hide();
}
function showAddAlbumModel(){
    $('#addAlbumModel').show();
}
function addAlbum(){
    var name = $('#albumName').val();
    hideAddAlbumModel();
    $('#albumName').val('');
    $.ajax({
        url: "album/addAlbum",
        type: "POST",
        dataType: "json",
        data: {
        name:name
        },
        success: function (res) {
            if(res){
                getAlbums();
            }
        }
    })
}
function addInputFileListener(){
    $('#fakeInputGroupFile').on('click',function(e){
    //这里需要一个假input作前端样式
    //触发假input后弹出选择相册模块
    //选择完成后触发真正input框进行选择文件并上传
    //阻止假input触发默认行为（打开文件选择框）
    e.preventDefault();
    $('#selectModel').show();
    });
}
//添加监听 以及初始化
isLogin();
addInputFileListener();
addhashListener();
changeMain();
hideSelect();
hideAddAlbumModel();
getPhotos();
getAlbums();

//vue相关
var vphoto = new Vue({
    el: "#photosGroup",
    data: {
        photosData: []
    }
});
var vtoastDelete = new Vue({
    el: "#toastDelete",
    data: {
        successful: 1
    }
})
var vtoastUpload = new Vue({
    el: "#toastUpload",
    data: {
        total: 0,
        successful: 0,
        aborted: 0
    }
})
var vAlbumListTab = new Vue({
    el: "#list-tab",
    data: {
        msg:"aaa",
        albums:[
            {id:"1",name:"asd"},
            {id:"1",name:"asd"},
            {id:"1",name:"asd"}
        ]
    }
})
var valbum = new Vue({
    el:"#albumsGroup",
    data:{
        albums:[
        //     { id:1,name:"默认相册1",size:23,previewPhotoPath:"https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u=2609405129,927497876&fm=74&app=80&f=JPEG&size=f121,90?sec=1880279984&t=bfa4734a31abde3ad80e216835386096"},
        //     { id:2,name:"默认相册2",size:23,previewPhotoPath:"https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u=3225612437,610756914&fm=74&app=80&f=JPEG&size=f121,121?sec=1880279984&t=ace7049a5d54601ef0652a84037bf53d"},
        //     { id:3,name:"默认相册3",size:23,previewPhotoPath:"https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u=1435177931,3550932454&fm=74&app=80&f=PNG&size=f121,121?sec=1880279984&t=951d17d26356c05751a439b31c267d8b"}
        ]
    }
})


layui.use(['upload','layer'], function () {
    var upload = layui.upload;
    var layer = layui.layer;

    layer.photos({
        photos: '#photosGroup'
        ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
      }); 
    //执行实例
    var uploadInst = upload.render({
        elem: '#inputGroupFile' //绑定元素
        , url: '/photo/upload' //上传接口
        , accept: 'images'
        , data:{
            aid:function(){
            return $('#selected').val();
        }
          }
        , acceptMime: 'image/*'
        , size: 10240
        , multiple: true
        , progress: function(n,elem){

            if(Number($('#totalfiles').html())==0)
            $('#totalfiles').html(elem.files.length);

            $('#progress-bar').attr('style','width:'+n+"%;");
            $('#progress-bar').attr('aria-valuenow',n);
            $('#progress-bar').html(n+'%');
            $('#toastProgress').toast('show');
            
          }
        , allDone: function (obj) { //当文件全部被提交后，才触发
            vtoastUpload.total = obj.total
            vtoastUpload.successful = obj.successful
            vtoastUpload.aborted = obj.aborted
            //   console.log(obj.total); //得到总文件数
            //   console.log(obj.successful); //请求成功的文件数
            //   console.log(obj.aborted); //请求失败的文件数
            getPhotos();
            getAlbums();
            $('#toastUpload').toast('show');
            $('#fileindex').html(0);
            $('#totalfiles').html(0);
            // $('#toastProgress').toast('hide');
        }
        , done: function (res, index, upload) { //每个文件提交一次触发一次。详见“请求成功的回调”
            $('#fileindex').html(Number($('#fileindex').html())+1);
            // $('#progress-text').html('正在上传第'+index+"张")
        }
        , error: function () {
            //请求异常回调
        }
    });
});

