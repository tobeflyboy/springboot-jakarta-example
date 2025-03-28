function setActiveMenu() {
    const currentPath = window.location.pathname;

    // 遍历所有菜单项
    $('#side-menu li a').each(function () {
        // 获取菜单项的 href 属性
        const href = $(this).attr('href');

        // 判断 href 是否包含当前路径
        if (currentPath.indexOf(href) !== -1) {
            // 为匹配的菜单项添加 active 类
            const $li = $(this).closest('li');
            $li.addClass('active');

            // 向上查找父级菜单，直到找到 #side-menu 或最上级的 <li>，并为每个父级菜单项添加 active 类
            $li.parents('li').each(function () {
                const $parentLi = $(this);

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
 * 初始化左侧导航栏
 */
function initLeftNav() {
    //加载左侧导航
    $("#leftnav").load(ctx + '/leftnav', function (response, status, xhr) {
        if (status === 'success') {
            //加载完成执行导航组件
            $('#side-menu').metisMenu();
            // 调用函数设置当前 URL 对应的菜单项的 active 样式
            setActiveMenu();
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
    });
}

$(document).ready(function () {
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
        const ibox = $(this).closest('div.ibox');
        const button = $(this).find('i');
        const content = ibox.find('div.ibox-content');
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
        const content = $(this).closest('div.ibox');
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
        const button = $(this).find('i');
        const label = $(this).next('span');
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
        const heightWithoutNavbar = $("body > #wrapper");
        $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");

        const navbarHeigh = $('nav.navbar-default').height() + 61;
        const wrapperHeigh = $('#page-wrapper').height() + 61;

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

        const collapse = localStorage.getItem("collapse_menu");
        const fixedsidebar = localStorage.getItem("fixedsidebar");
        const fixednavbar = localStorage.getItem("fixednavbar");
        const boxedlayout = localStorage.getItem("boxedlayout");
        const fixedfooter = localStorage.getItem("fixedfooter");

        const body = $('body');

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
}

// 自定义 confirm 弹窗函数
function showConfirm(message, title = '确认操作', onConfirm, onCancel) {
    // 创建模态框
    const modalHtml = `
        <div class="modal fade" id="customConfirmModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">${title}</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>${message}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="confirmBtn">确认</button>
                    </div>
                </div>
            </div>
        </div>
    `;

    // 将模态框添加到页面
    $('body').append(modalHtml);

    // 显示模态框
    $('#customConfirmModal').modal('show');

    // 确认按钮点击事件
    $('#confirmBtn').on('click', function () {
        $('#customConfirmModal').modal('hide');
        if (typeof onConfirm === 'function') {
            onConfirm();
        }
    });

    // 取消按钮点击事件
    $('#customConfirmModal').on('hidden.bs.modal', function () {
        if (typeof onCancel === 'function') {
            onCancel();
        }
        // 移除模态框
        $(this).remove();
    });
}

