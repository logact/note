define(['web/base/service', 'taggle', 'css!web/lib/prettyCheckbox/pretty-checkbox'], function () {
    var app = angular.module('cnv.directives', ['cnv.services']);

    app.directive('tabs', ['template', '$timeout',
        function (template, $timeout) {
            return {
                'restrict': 'EA',
                transclude: true,
                replace: true,
                scope: {
                    styleClass: '@'
                },
                templateUrl: template.getUrl('tabs'),
                link: function ($scope, element, attrs) {
                    if (attrs.styleClass) {
                        element.addClass(attrs.styleClass);
                    }
                },
                controller: ["$scope", function ($scope) {
                    var panes = $scope.panes = [];

                    $scope.select = function (pane) {
                        angular.forEach(panes, function (pane) {
                            pane.selected = false;
                        });
                        pane.selected = true;
                        if (_.isFunction(pane.onShow)) {
                            $timeout(function () {
                                pane.onShow.call(pane);
                            }, 150);
                        }
                    }

                    this.addPane = function (pane) {
                        if (panes.length == 0) $scope.select(pane);
                        panes.push(pane);
                    }
                }]
            };
        }])

    app.directive('tab', [function () {
        return {
            require: '^tabs',
            restrict: 'E',
            transclude: true,
            scope: {
                title: '@',
                // use on-show ,as angular auto transform to on-show
                onShow: '&'
            },
            link: function ($scope, element, attrs, tabsCtrl) {
                tabsCtrl.addPane($scope, element);
            },
            template: '<div class="tab-pane" ng-class="{active: selected}" ng-transclude></div>',
            replace: true
        };
    }]);

    app.directive('widget', ['template', function (template) {
        return {
            'restrict': 'EA',
            transclude: true,
            replace: true,
            templateUrl: template.getUrl('directive_widget'),
            scope: {
                title: '@',
            },
            link: function ($scope, element, attrs) {
            }
        };
    }]);

    app.directive('field', ['template', '$timeout', 'dialog', function (template, $timeout, dialog) {
        return {
            'restrict': 'E',
            transclude: true,
            replace: true,
            scope: {
                styleClass: '@',
                label: '@',
                ngModel: '=',
                info: '@',
                copy: '=',
                width: '@',
            },
            templateUrl: template.getUrl('directive_field'),
            link: function ($scope, element, attrs) {
                if (attrs.styleClass) {
                    element.addClass(attrs.styleClass);
                }
                if (_.isArray($scope.ngModel)) {
                    $scope.ngModel = _.join($scope.ngModel, ',');
                }

                if (attrs.width) {
                    var width = _.parseInt(attrs.width);
                    if (_.isNumber(width)) {
                        element.find('.c-field-label').width(width);
                        element.find('.c-field-value').css('marginLeft', width + 15);
                    }
                }

                if (attrs.info) {
                    $timeout(function () {
                        var tipElement = element.find('.control-info');
                        var myOpentip = new Opentip(tipElement, {
                            myOpentip: 'right'
                        });
                        myOpentip.setContent(attrs.info);
                    });
                }
                if ($scope.copy) {
                    $scope.copyField = function (event) {
                        var copyContent = $(event.currentTarget).parent().parent().siblings(".c-field-value").text();
                        var $temp = $("<input>");
                        $("body").append($temp);
                        $temp.val(copyContent).select();
                        document.execCommand("copy");
                        $temp.remove();
                        dialog.noty("复制成功")
                    };
                }
            }
        }
    }]);

    app.directive('viewform', ['template', function (template) {
        return {
            'restrict': 'EA',
            'templateUrl': template.getUrl('directive_viewform'),
            'replace': true,
            'transclude': true,
            scope: false,
            compile: function (element, attrs) {
            }
        }
    }]);

    app.directive('cnvtextarea', ['template', '$timeout', function (template, $timeout) {
        return {
            'restrict': 'EA',
            replace: true,
            scope: {
                styleClass: '@',
                label: '@',
                ngModel: '=',
                required: '@',
                info: '@',
                width: '@',
                placeholder: '@'
            },
            templateUrl: template.getUrl('directive_textarea'),
            link: function ($scope, element, attrs) {
                if (attrs.styleClass) {
                    element.addClass(attrs.styleClass);
                }

                if (attrs.width) {
                    var width = _.parseInt(attrs.width);
                    if (_.isNumber(width)) {
                        element.find('.form-label').width(width);
                        element.find('.form-input').css('marginLeft', width + 15);
                    }
                }

                if (attrs.required) {
                    element.addClass('required');
                }

                if (attrs.info) {
                    $timeout(function () {
                        var tipElement = element.find('.control-info');
                        var myOpentip = new Opentip(tipElement, {
                            myOpentip: 'right'
                        });
                        myOpentip.setContent(attrs.info);
                    });
                }
            }
        }
    }]);

    app.directive('cnvtext', ['template', '$timeout',
        function (template, $timeout) {
            return {
                'restrict': 'EA',
                transclude: true,
                replace: true,
                scope: {
                    styleClass: '@',
                    label: '@',
                    ngModel: '=',
                    required: '@',
                    info: '@',
                    width: '@',
                    placeholder: '@',
                    requiredfield: '@',
                },
                templateUrl: template.getUrl('directive_cnvtext'),
                link: function ($scope, element, attrs) {
                    if (attrs.styleClass) {
                        element.addClass(attrs.styleClass);
                    }

                    if (attrs.width) {
                        var width = _.parseInt(attrs.width);
                        if (_.isNumber(width)) {
                            element.find('.form-label').width(width);
                            element.find('.form-input').css('marginLeft', width + 15);
                        }
                    }

                    if (attrs.required) {
                        element.addClass('required');
                    }

                    if (attrs.info) {
                        $timeout(function () {
                            var tipElement = element.find('.control-info');
                            var myOpentip = new Opentip(tipElement, {
                                myOpentip: 'right'
                            });
                            myOpentip.setContent(attrs.info);
                        });
                    }
                }
            }
        }]);

    app.directive('cnvtags', ['template', 'DBUtils',
        function (template, DBUtils) {
            return {
                'restrict': 'EA',
                transclude: true,
                replace: true,
                scope: {
                    tagKey: '@',
                    label: '@',
                    ngModel: '=ngModel'
                },
                controllerAs: 'vm',
                templateUrl: template.getUrl('directive_cnvtags'),
                link: function ($scope, element, attrs) {
                    $scope.label = attrs.label;
                },
                controller: ['$scope', '$element',
                    function ($scope, $element) {
                        var vm = this;
                        vm.items = [];
                        vm.tagKey = $scope.tagKey;

                        $scope.showClear = false;

                        if (!$scope.ngModel) {
                            $scope.ngModel = [];
                        }

                        var ctrl = {
                            initialize: function () {
                                ctrl.loadTags();
                            },
                            loadTags: function () {
                                DBUtils.find('dataTag', {
                                    key: vm.tagKey
                                }).success(function (data) {
                                    var arr = _.get(data, "datas.result.tags", []);
                                    vm.items = arr;
                                    ctrl.bindSelect();
                                });
                            },
                            addTag: function (tagName) {
                                if (!~_.indexOf(vm.items, tagName)) {
                                    DBUtils.update('dataTag', {
                                        key: vm.tagKey
                                    }, {
                                        $addToSet: {
                                            'tags': tagName
                                        }
                                    }, true).success(function () {
                                        vm.items.push(tagName);
                                    });
                                }

                                if (!~_.indexOf($scope.ngModel, tagName)) {
                                    $scope.ngModel.push(tagName);
                                }
                            },
                            removeTag: function (tagName) {
                                _.remove(vm.items, function (item) {
                                    return item === tagName;
                                });
                                _.remove($scope.ngModel, function (item) {
                                    return item === tagName;
                                });
                            },
                            bindSelect: function () {
                                var tagSelect = $element.find('div.taggle_input')[0];
                                var taggleWidget = new Taggle(tagSelect, {
                                    tags: $scope.ngModel,
                                    saveOnBlur: true,
                                    onTagAdd: function (event, tag) {
                                        ctrl.addTag(tag);
                                    },
                                    onTagRemove: function (event, tag) {
                                        ctrl.removeTag(tag);
                                    }
                                });

                                var autocompleteInput = $element.find('input.taggle_input');
                                var appendTo = $element.find('div.taggle_input');
                                autocompleteInput.autocomplete({
                                    source: vm.items,
                                    appendTo: appendTo,
                                    position: {at: "left bottom", of: appendTo},
                                    select: function (event, data) {
                                        event.preventDefault();
                                        //Add the tag if user clicks
                                        if (event.which === 1) {
                                            taggleWidget.add(data.item.value);
                                        }
                                    }
                                });
                            }
                        }

                        ctrl.initialize();
                    }]
            }
        }]);
    app.directive('cnvdate', ['template', '$timeout',
        function (template, $timeout) {
            return {
                'restrict': 'EA',
                transclude: true,
                replace: true,
                scope: {
                    styleClass: '@',
                    label: '@',
                    ngModel: '=',
                    required: '@',
                    info: '@',
                    width: '@',
                    timepicker: '@',
                    placeholder: '@'
                },
                templateUrl: template.getUrl('directive_cnvdate'),
                link: function ($scope, element, attrs) {
                    if (attrs.styleClass) {
                        element.addClass(attrs.styleClass);
                    }
                    if (!attrs.width) {
                        attrs.width = '200px';
                    } else {
                        attrs.width = attrs.width + '';
                        if (attrs.width.indexOf('px') === -1) {
                            attrs.width = attrs.width + 'px';
                        }
                    }

                    if (attrs.required) {
                        element.addClass('required');
                    }

                    if (attrs.info) {
                        $timeout(function () {
                            var tipElement = element.find('.control-info');
                            var myOpentip = new Opentip(tipElement, {
                                myOpentip: 'right'
                            });
                            myOpentip.setContent(attrs.info);
                        });
                    }

                    if (attrs.timepicker === 'true') {
                        attrs.timepicker = true;
                    } else {
                        attrs.timepicker = false;
                    }

                    $timeout(function () {
                        var format = 'Y-m-d';
                        if (attrs.timepicker) {
                            format += ' H:i';
                        }
                        element.find('.form-control').datetimepicker({
                            format: format,
                            timepicker: attrs.timepicker
                        });
                    });
                }
            }
        }]);

    app.directive('cnvpwd', ['template', '$timeout',
        function (template, $timeout) {
            return {
                'restrict': 'EA',
                transclude: true,
                replace: true,
                scope: {
                    styleClass: '@',
                    label: '@',
                    ngModel: '=',
                    required: '@',
                    info: '@',
                    width: '@'
                },
                templateUrl: template.getUrl('directive_pwd'),
                link: function ($scope, element, attrs) {

                    if (attrs.styleClass) {
                        element.addClass(attrs.styleClass);
                    }

                    if (!attrs.width) {
                        attrs.width = '200px';
                    } else {
                        attrs.width = attrs.width + '';
                        if (attrs.width.indexOf('px') === -1) {
                            attrs.width = attrs.width + 'px';
                        }
                    }

                    if (attrs.required) {
                        element.addClass('required');
                    }

                    if (attrs.info) {
                        $timeout(function () {
                            var tipElement = element.find('.control-info');
                            var myOpentip = new Opentip(tipElement, {
                                myOpentip: 'right'
                            });
                            myOpentip.setContent(attrs.info);
                        });
                    }
                }
            }
        }]);

    app.directive('cnvcheckbox', ['template', '$timeout', function (template, $timeout) {
        return {
            'restrict': 'EA',
            transclude: true,
            replace: true,
            scope: {
                styleClass: '@',
                label: '@',
                checked: '&?',
                disabled: '&?',
                ngModel: '=ngModel',
                info: "@"
            },
            controllerAs: 'vm',
            bindToController: true,
            template: template.get("directive_cnvcheckbox"),
            link: function ($scope, element, attrs) {
                if (attrs.styleClass) {
                    element.addClass(attrs.styleClass);
                }

                if (attrs.info) {
                    $timeout(function () {
                        var tipElement = element.find('.control-info');
                        var myOpentip = new Opentip(tipElement, {
                            myOpentip: 'right'
                        });
                        myOpentip.setContent(attrs.info);
                    });
                }
            },
            controller: ['$scope', function ($scope) {
                var vm = this;

                if (angular.isFunction(vm.checked)) {
                    vm.ngModel = !!vm.checked();
                }

                vm.toggle = function () {
                    if (angular.isFunction(vm.disabled) && vm.disabled()) return;
                    vm.ngModel = !vm.ngModel;
                }
            }]
        }
    }]);

    app.directive('cnvnumber', ['template', '$timeout', function (template, $timeout) {
        return {
            'restrict': 'EA',
            transclude: true,
            replace: true,
            scope: {
                styleClass: '@',
                label: '@',
                ngModel: '=',
                required: '@',
                info: '@',
                width: '@',
                placeholder: '@'
            },
            template: template.get("directive_cnvnumber"),
            link: function ($scope, element, attrs) {
                if (attrs.styleClass) {
                    element.addClass(attrs.styleClass);
                }
                if (!attrs.width) {
                    attrs.width = '200px';
                } else {
                    attrs.width = attrs.width + '';
                    if (attrs.width.indexOf('px') === -1) {
                        attrs.width = attrs.width + 'px';
                    }
                }

                if (attrs.required) {
                    element.addClass('required');
                }

                element.find('input[type="text"]').keydown(function (e) {
                    // Allow: backspace, delete, tab, escape, enter and .
                    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
                        // Allow: Ctrl+A
                        (e.keyCode == 65 && e.ctrlKey === true) ||
                        // Allow: Ctrl+C
                        (e.keyCode == 67 && e.ctrlKey === true) ||
                        // Allow: Ctrl+X
                        (e.keyCode == 88 && e.ctrlKey === true) ||
                        // Allow: home, end, left, right
                        (e.keyCode >= 35 && e.keyCode <= 39)) {
                        // let it happen, don't do anything
                        return;
                    }
                    // Ensure that it is a number and stop the keypress
                    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                        e.preventDefault();
                    }
                });

                if (attrs.info) {
                    $timeout(function () {
                        var tipElement = element.find('.control-info');
                        var myOpentip = new Opentip(tipElement, {
                            myOpentip: 'right'
                        });
                        myOpentip.setContent(attrs.info);
                    });
                }
            }
        }
    }]);

    app.directive('cnvselect', ['template', '$timeout', function (template, $timeout) {
        return {
            'restrict': 'EA',
            transclude: true,
            replace: true,
            scope: {
                styleClass: '@',
                label: '@',
                ngModel: '=',
                required: '@',
                options: '=',
                info: '@',
                ngDisabled: '=',
                defaultValue: '='
            },
            templateUrl: template.getUrl('directive_cnvselect'),
            controller: ['$scope', function ($scope) {
                let value = $scope.defaultValue;
                let ngModel = $scope.ngModel;
                if (value && !ngModel) {
                    $scope.ngModel = $scope.defaultValue;
                }
            }],
            link: function ($scope, element, attrs) {
                if (attrs.styleClass) {
                    element.addClass(attrs.styleClass);
                }

                if (attrs.info) {
                    $timeout(function () {
                        var tipElement = element.find('.control-info');
                        var myOpentip = new Opentip(tipElement, {
                            myOpentip: 'right'
                        });
                        myOpentip.setContent(attrs.info);
                    });
                }
            }
        }
    }]);


    app.directive('list', ['template', '$timeout', '$stateParams', function (template, $timeout, $stateParams) {
        return {
            'restrict': 'EA',
            transclude: true,
            replace: true,
            scope: {
                options: '='
            },
            template: template.get('directive_list'),
            controller: ['$scope', '$element', '$attrs',
                function ($scope, $element, $attrs) {
                    $scope.reload = function () {
                        var table = $element.find('.c-table');
                        table.DataTable().ajax.reload();
                    };
                    $scope.initReload = function () {
                        $element.find('.search-input').val('');
                        var table = $element.find('.c-table');
                        table.DataTable().ajax.reload();
                    };

                    if ($scope.options.toolbar !== false) {
                        $scope.options.toolbar = true;
                    }
                    if ($scope.options.columns) {
                        _.each($scope.options.columns, function (column) {
                            column.data = column.data || column.name;
                        });
                    }

                    if ($scope.options.filled === true) {
                        $element.addClass('filled');
                    }

                    var options = {};
                    var key = $stateParams.menuKey;
                    options = _.extend(options, $scope.options);
                    if (!options.data) {
                        /*Ajax calling*/
                        options = _.extend(options, {
                            "processing": true,
                            "serverSide": true,
                            "ajax": {
                                type: 'post',
                                url: "dbs/pagelist",
                                data: {
                                    collection: options.collection
                                },
                                beforeSend: function (event, ajaxSetting) {
                                    var filterObject = {
                                        filter: {
                                            query: {},
                                            keyword: ''
                                        },
                                        params: {}
                                    };
                                    var projectorObject = {};
                                    if (_.isObject(options.query)) {
                                        filterObject.filter.query = options.query;
                                    }
                                    if (_.isFunction(options.query)) {
                                        filterObject.filter.query = options.query.call(null);
                                    }
                                    var columns = _.map(options.columns, function (col) {
                                        return {
                                            name: col.name || '',
                                            data: col.data || col.name,
                                            query: col.query || true,
                                            search: col.search || false
                                        }
                                    });
                                    filterObject.columns = encodeURI(JSON.stringify(columns));
                                    var keyword = $element.find('.search-input').val();
                                    if (keyword) {
                                        filterObject.filter.keyword = encodeURIComponent(keyword);
                                    }

                                    var table = $element.find('.c-table').DataTable();
                                    var order = table.order();

                                    var name = _.get(table.settings().init().columns, _.get(order, '0.0', 0), '').name || '';
                                    filterObject.order = name;
                                    filterObject.asc = _.isEqual(_.get(order, '0.1', 'asc'), 'asc');

                                    $scope.$emit('beforeQuery.list', options, ajaxSetting, filterObject);
                                    /*Append the data*/
                                    if (filterObject) {
                                        var paramArr = [];
                                        _.each(filterObject, function (value, key) {
                                            if (value) {
                                                var item = key + '=';
                                                if (_.isObject(value)) {
                                                    value = JSON.stringify(value);
                                                }
                                                item += value;
                                                paramArr.push(item);
                                            }
                                        });
                                        ajaxSetting.data += ('&' + paramArr.join('&'));
                                    }
                                }
                            }
                        });
                    }
                    options.columnDefs = options.columnDefs || [];
                    options.columnDefs.push({
                        defaultContent: '',
                        targets: '_all'
                    })
                    options = _.extend(options, {
                        "bLengthChange": false,
                        "bFilter": false,
                        searching: false,
                        orderMulti: false,
                        "iDisplayLength": 10,
                        pagingType: 'simple_numbers',
                        "language": {
                            "sProcessing": "处理中...",
                            "sLengthMenu": "显示 _MENU_ 项结果",
                            "sZeroRecords": "没有匹配结果",
                            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                            "sInfoFiltered": "",
                            "sInfoPostFix": "",
                            "sSearch": "搜索:",
                            "sUrl": "",
                            "sLoadingRecords": "载入中...",
                            "sInfoThousands": ",",
                            sEmptyTable: '<div class="dataTable_empty_content"><div class="d_e_img"></div><div class="d_e_text">暂无数据记录</div></div>',
                            "oPaginate": {
                                "sFirst": "首页",
                                "sPrevious": "<",
                                "sNext": ">",
                                "sLast": "末页"
                            },
                            "oAria": {
                                "sSortAscending": ": 以升序排列此列",
                                "sSortDescending": ": 以降序排列此列"
                            }
                        }
                    });

                    function initialize() {
                        var table = $element.find('.c-table');
                        var datatable = table.DataTable(options);

                        $scope.$emit('beforeLoad');
                        table.on('xhr.dt', function (event, options, jsondata, ajax) {
                            if (jsondata && jsondata.data) {
                                _.each(jsondata.data, function (row) {
                                    row.DT_RowId = row.id || '';
                                });
                            }
                            $scope.$broadcast('afterQuery.list', jsondata);
                        });

                        table.on('click', 'button,a', function (event) {
                            var rowdata = table.DataTable().row($(this).parents('tr')).data();
                            $scope.$emit('buttonClicked.list', $(event.target), options, rowdata);
                        });
                    }

                    initialize();
                }],
            link: function ($scope, element, attrs) {
                $timeout(function () {
                    element.find('.search-input').keydown(function (event) {
                        if (event.keyCode == 13) {
                            $scope.reload();
                        }
                    });
                }, 500)
            }
        }
    }]);

    app.directive('keyEnter', function () {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    scope.$apply(function () {
                        scope.$eval(attrs.keyEnter);
                    });

                    event.preventDefault();
                }
            });
        };
    });
});
