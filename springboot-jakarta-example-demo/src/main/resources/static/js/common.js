/**
 * 获取指定名称的 Cookie 值
 * @param {string} name - Cookie 名称
 * @returns {string|null} - Cookie 值，如果不存在则返回 null
 */
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}

/**
 * 解析 SessionUser 对象
 * @param {string} sessionUserCookie - SessionUser 的 Cookie 值
 * @returns {object|null} - 解析后的 SessionUser 对象，如果解析失败则返回 null
 */
function parseSessionUser(sessionUserCookie) {
    try {
        // Base64 解码
        const decodedSessionUser = atob(sessionUserCookie);
        // JSON 反序列化
        return JSON.parse(decodedSessionUser);
    } catch (error) {
        console.error('Failed to parse SessionUser:', error);
        return null;
    }
}


function setActiveMenu() {
    var currentPath = window.location.pathname;

    // 遍历所有菜单项
    $('#side-menu li a').each(function () {
        // 获取菜单项的 href 属性
        var href = $(this).attr('href');

        // 判断 href 是否包含当前路径
        if (currentPath.indexOf(href) !== -1) {
            // 为匹配的菜单项添加 active 类
            var $li = $(this).closest('li');
            $li.addClass('active');

            // 向上查找父级菜单，直到找到 #side-menu 或最上级的 <li>，并为每个父级菜单项添加 active 类
            $li.parents('li').each(function () {
                var $parentLi = $(this);

                // 如果找到了 #side-menu，则停止查找
                if ($parentLi.closest('#side-menu').length > 0) {
                    // 为父级菜单添加 active 类
                    $parentLi.addClass('active');

                    // 如果父级菜单有子菜单，展开子菜单（添加 'in' 类）
                    $parentLi.children('ul').addClass('in');
                }
            });

            // 如果菜单项有子菜单，展开子菜单
            $(this).closest('li').children('ul').addClass('in');
        } else {
            // 移除没有匹配的菜单项的 active 类
            $(this).closest('li').removeClass('active');

            // 关闭子菜单
            $(this).closest('li').children('ul').removeClass('in');
        }
    });
}

/**
 * 生成菜单项
 * @param {Array} oneLevelMenus - 菜单权限数据
 */
function generateMenu(oneLevelMenus) {
    oneLevelMenus.forEach(oneLevelMenu => {
        // 创建菜单项的 <li> 元素
        let menu = '<li>';
        // 生成菜单链接
        const oneLeveMenuUrl = oneLevelMenu.url ? `${ctx}/${oneLevelMenu.url}` : 'javascript:void(0)';
        menu += `<a href="${oneLeveMenuUrl}">`;
        menu += `<i class="fa ${oneLevelMenu.icon}"></i> <span class="nav-label">${oneLevelMenu.permissionName}</span>`;
        if (oneLevelMenu.children && oneLevelMenu.children.length > 0) {
            menu += '<span class="fa arrow"></span>';
            menu += '<ul class="nav nav-second-level collapse">';
            oneLevelMenu.children.forEach(twoLevelMenus => {
                menu += '<li>';
                const twoLevelMenuUrl = twoLevelMenus.url ? `${ctx}/${twoLevelMenus.url}` : 'javascript:void(0)';
                menu += `<a href="${twoLevelMenuUrl}">`;
                menu += `<i class="fa ${twoLevelMenus.icon}"></i><span class="nav-label">${twoLevelMenus.permissionName}</span>`;
                if (twoLevelMenus.children && twoLevelMenus.children.length > 0) {
                    menu += '<span class="fa arrow"></span></a>';
                    menu += '<ul class="nav nav-third-level">';
                    twoLevelMenus.children.forEach(threeLevelMenus => {
                        menu += '<li>';
                        const threeLevelMenuUrl = threeLevelMenus.url ? `${ctx}/${threeLevelMenus.url}` : 'javascript:void(0)';
                        menu += `<a href="${threeLevelMenuUrl}">`;
                        menu += `<i class="fa ${threeLevelMenus.icon}"></i>${threeLevelMenus.permissionName}`;
                        menu += '</a></li>';
                    });
                    menu += '</ul>';
                } else {
                    menu += '</a></li>';
                }

            });
        } else {
            menu += '</a>';
            menu += '</li>';
        }
        $("#side-menu").append(menu);
    });
}

/**
 * 初始化左侧导航栏
 */
function initLeftNav() {
    // 使用模板字符串生成左侧导航栏结构
    const sidebarScroll = `
        <div class="sidebar-scroll">
            <div class="logo">
                <a href="${ctx}/dashboard">
                    <img src="${ctx}/static/images/logo_icon.png" style="width: 55px;height: 40px;">Demo
                </a>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav metismenu" id="side-menu"></ul>
            </div>
        </div>
    `;
    $("#leftnav").html(sidebarScroll);
    // 生成菜单项
    generateMenu(sessionUser.permissions);
    // 初始化 MetisMenu（依赖 jQuery）
    $('#side-menu').metisMenu();
    // 设置当前 URL 对应的菜单项的 active 样式
    setActiveMenu();

    // 如果页面有 fixed-sidebar 类，则初始化 slimScroll
    if ($("body").hasClass('fixed-sidebar')) {
        $('.sidebar-scroll').slimScroll({
            height: '100%',
            railVisible: false,
            color: "#65cea7",
            opacity: .8,
            size: '4px',
            borderRadius: '0',
            railBorderRadius: '0',
            distance: 0
        });
    }
}

$(document).ready(function () {

    // 获取 ContextPath 的 Cookie 值，并赋值给全局变量 ctx
    // window.ctx = getCookie('ctx');
    // // 获取 SessionUser 的 Cookie 值
    // const sessionUserCookie = getCookie('sessionUser');
    // if (sessionUserCookie) {
    //     // 解析 SessionUser
    //     window.sessionUser = parseSessionUser(sessionUserCookie);
    // }
    console.log(ctx);
    console.log(sessionUser);

    //初始化左侧导航栏
    initLeftNav()

    // 使用模板字符串生成左侧导航栏结构
    const navbarHeader = `
        <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="javascript:void(0)">
                <i class="fa fa-bars"></i>
            </a>
        </div>
        <ul class="nav navbar-top-links navbar-right notification-menu">
            <li class="sysUserDo-dropdown">
                <a href="javascript:void(0)" class="btn  dropdown-toggle" data-toggle="dropdown"> 
                    <span>${sessionUser.realName}</span>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                    <li>
                        <a href="${ctx}/logout">
                            <i class="fa fa-sign-out"></i>退出
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    `;
    $("#topnav").html(navbarHeader);
    //加载完成执行按钮点击事件
    $('.navbar-minimalize').click(function () {
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });

    // Add body-small class if window less than 768px
    if ($(this).width() < 769) {
        $('body').addClass('body-small')
    } else {
        $('body').removeClass('body-small')
    }
    // Collapse ibox function
    $('.collapse-link').click(function () {
        var ibox = $(this).closest('div.ibox');
        var button = $(this).find('i');
        var content = ibox.find('div.ibox-content');
        content.slideToggle(200);
        button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
        ibox.toggleClass('').toggleClass('border-bottom');
        setTimeout(function () {
            ibox.resize();
            ibox.find('[id^=map-]').resize();
        }, 50);
    });

    // Close ibox function
    $('.close-link').click(function () {
        var content = $(this).closest('div.ibox');
        content.remove();
    });

    // Close menu in canvas mode
    $('.close-canvas-menu').click(function () {
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });

    // Open close right sidebar
    $('.right-sidebar-toggle').click(function () {
        $('#right-sidebar').toggleClass('sidebar-open');
    });

    // Initialize slimscroll for right sidebar
    $('.sidebar-container').slimScroll({
        height: '100%',
        railOpacity: 0.4,
        wheelStep: 10
    });

    // Open close small chat
    $('.open-small-chat').click(function () {
        $(this).children().toggleClass('fa-comments').toggleClass('fa-remove');
        $('.small-chat-box').toggleClass('active');
    });

    // Initialize slimscroll for small chat
    $('.small-chat-box .content').slimScroll({
        height: '234px',
        railOpacity: 0.4
    });

    $('.check-link').click(function () {
        var button = $(this).find('i');
        var label = $(this).next('span');
        button.toggleClass('fa-check-square').toggleClass('fa-square-o');
        label.toggleClass('todo-completed');
        return false;
    });

    // Tooltips demo
    $('.tooltip-demo').tooltip({
        selector: "[data-toggle=tooltip]",
        container: "body"
    });

    // Move modal to body
    // Fix Bootstrap backdrop issu with animation.css
    $('.modal').appendTo("body");

    // Full height of sidebar
    function fix_height() {
        var heightWithoutNavbar = $("body > #wrapper");
        $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");

        var navbarHeigh = $('nav.navbar-default').height() + 61;
        var wrapperHeigh = $('#page-wrapper').height() + 61;

        if (navbarHeigh > wrapperHeigh) {
            $('#page-wrapper').css("min-height", navbarHeigh + "px");
        }

        if (navbarHeigh < wrapperHeigh) {
            $('#page-wrapper').css("min-height", $(window).height() + "px");
        }

        if ($('body').hasClass('fixed-nav')) {
            $('#page-wrapper').css("min-height", $(window).height() + "px");
        }

    }

    fix_height();

    // Move right sidebar top after scroll
    $(window).scroll(function () {
        if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav')) {
            $('#right-sidebar').addClass('sidebar-top');
        } else {
            $('#right-sidebar').removeClass('sidebar-top');
        }
    });

    $(window).bind("load resize scroll", function () {
        if (!$("body").hasClass('body-small')) {
            fix_height();
        }
    });

    $("[data-toggle=popover]").popover();

    // Add slimscroll to element
    $('.full-height-scroll').slimscroll({
        height: '100%'
    });

    if (localStorageSupport) {

        var collapse = localStorage.getItem("collapse_menu");
        var fixedsidebar = localStorage.getItem("fixedsidebar");
        var fixednavbar = localStorage.getItem("fixednavbar");
        var boxedlayout = localStorage.getItem("boxedlayout");
        var fixedfooter = localStorage.getItem("fixedfooter");

        var body = $('body');

        if (fixedsidebar === 'on') {
            body.addClass('fixed-sidebar');
            $('.sidebar-collapse').slimScroll({
                height: '100%',
                railOpacity: 0.9
            });
        }

        if (collapse === 'on') {
            if (body.hasClass('fixed-sidebar')) {
                if (!body.hasClass('body-small')) {
                    body.addClass('mini-navbar');
                }
            } else {
                if (!body.hasClass('body-small')) {
                    body.addClass('mini-navbar');
                }

            }
        }

        if (fixednavbar === 'on') {
            $(".navbar-static-top").removeClass('navbar-static-top').addClass('navbar-fixed-top');
            body.addClass('fixed-nav');
        }

        if (boxedlayout === 'on') {
            body.addClass('boxed-layout');
        }

        if (fixedfooter === 'on') {
            $(".footer").addClass('fixed');
        }
    }
});


// Minimalize menu when screen is less than 768px
$(window).bind("resize", function () {
    if ($(this).width() < 769) {
        $('body').addClass('body-small')
    } else {
        $('body').removeClass('body-small')
    }
});


// check if browser support HTML5 local storage
function localStorageSupport() {
    return (('localStorage' in window) && window['localStorage'] !== null)
}

function SmoothlyMenu() {
    if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
        // Hide menu in order to smoothly turn on when maximize menu
        $('#side-menu', '#side-head').hide();
        //$().hide();
        // For smoothly turn on menu
        setTimeout(
            function () {
                $('#side-menu', '#side-head').fadeIn(200);
            }, 200);
    } else if ($('body').hasClass('fixed-sidebar')) {
        $('#side-menu', '#side-head').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(400)

            }, 100);
    } else {
        // Remove all inline style from jquery fadeIn function to reset menu state
        $('#side-menu').removeAttr('style');
    }
};

