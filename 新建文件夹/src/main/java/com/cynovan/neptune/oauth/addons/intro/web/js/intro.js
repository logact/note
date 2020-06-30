define(['web/base/service', 'web/base/directive'], function () {
    var app = angular.module('intro', ['ngRoute', 'ngResource', 'ngSanitize', 'cnv.services', 'cnv.directives', 'ui.router']);

    app.config(['$routeProvider', '$locationProvider', '$controllerProvider', '$compileProvider', '$filterProvider', '$provide', '$httpProvider',
        function ($routeProvider, $locationProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $httpProvider) {
            app.controller = $controllerProvider.register;
            app.directive = $compileProvider.directive;
            app.filter = $filterProvider.register;
            app.factory = $provide.factory;
            app.service = $provide.service;

            $locationProvider.html5Mode(false);
            $locationProvider.hashPrefix('');

            function getTemplate(templateName) {
                var arr = ['/initialize/template/', templateName, '?v=', cynovan.version];
                return arr.join('');
            }

            function dependencyResolverFor(dependencies, path) {
                var definition = {
                    resolver: ['$q', '$rootScope', '$route', function ($q, $rootScope, $route) {
                        var deferred = $q.defer();
                        if (_.isFunction(dependencies)) {
                            dependencies = dependencies.call(null, $route.current.params);
                        }
                        require(dependencies, function () {
                            $rootScope.$apply(function () {
                                deferred.resolve();
                            });
                        });

                        return deferred.promise;
                    }]
                };
                return definition;
            }

            var config = {
                defaultRoutePaths: '/register',
                routes: {
                    '/register': {
                        templateUrl: function () {
                            return getTemplate('intro_register_template');
                        },
                        deps: ['css!intro/web/css/register', 'intro/web/js/register']
                    },
                    '/term': {
                        templateUrl: function () {
                            return getTemplate('intro_register_term_template');
                        },
                        deps: []
                    },
                    '/cfmreg': {
                        templateUrl: function () {
                            return getTemplate('intro_cfmreg_template');
                        },
                        deps: ['css!intro/web/css/cfmreg', 'intro/web/js/cfmreg']
                    },
                    '/code_expired': {
                        templateUrl: function () {
                            return getTemplate('code_expired');
                        },
                        deps: ['css!intro/web/css/codeExpired', 'intro/web/js/codeExpired']
                    },
                    '/joingroup': {
                        templateUrl: function () {
                            return getTemplate('intro_join_group_template');
                        },
                        deps: ['css!intro/web/css/joingroup', 'intro/web/js/joingroup']
                    },
                    '/retrievePwd': {
                        templateUrl: function () {
                            return getTemplate('retrievePwd_template');
                        },
                        deps: ['css!intro/web/css/retrievePwd', 'intro/web/js/retrievePwd']
                    },
                    '/joinCompany': {
                        templateUrl: function () {
                            return getTemplate("join_company_template");
                        },
                        deps: ['css!intro/web/css/joinCompany', 'intro/web/js/joinCompany']
                    },
                    '/invite': {
                        templateUrl: function () {
                            return getTemplate("invite_join_company_template");
                        },
                        deps: ['intro/web/js/inviteJoinCompany', 'css!intro/web/css/inviteJoinCompany']
                    }
                }
                }
            ;

            angular.forEach(config.routes, function (route, path) {
                $routeProvider.when(path, {
                    templateUrl: route.templateUrl,
                    resolve: dependencyResolverFor(route.deps, path)
                });
            });

            if (config.defaultRoutePaths !== undefined) {
                $routeProvider.otherwise({redirectTo: config.defaultRoutePaths});
            }
        }
    ]);
    return app;
});