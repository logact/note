var gulp = require('gulp');
var clean = require('gulp-clean');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
let babel = require('gulp-babel');
var rename = require('gulp-rename');
var cssmin = require('gulp-cssmin');
var randomstring = require("randomstring");
var replace = require('gulp-replace');
var sass = require('gulp-sass');
var fs = require('fs');

var json = JSON.parse(fs.readFileSync('./package.json'));
var path = json.path;

function pPath(arr) {
    return arr.map(function (p) {
        return path + p;
    })
}

var version = randomstring.generate({
    length: 8
});

gulp.task('watch', function () {
    console.log(' ============== watch sass ==================');
    console.log(' ============== sass started ==================');
    return gulp.watch(path + '/**/*.scss', ['sass']);
});

gulp.task('clean', function () {
    console.log('=======================Start Clean========================');

    var files = ['/**/*.min.js', '/**/*.min.css', '/web/dist', '/**/scss/*.css'];
    files = pPath(files);
    return gulp.src(files).pipe(clean({
        force: true
    }));
});

gulp.task('sass', ['clean'], function () {
    return gulp.src(path + '/**/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(rename({
            extname: '.css'
        }))
        .pipe(gulp.dest(function (data) {
            return data.base;
        }));
});

gulp.task('libsjs', [], function () {
    console.log('=======================Start Concat Lib JS File========================');

    var jsfiles = ['jquery/jquery.js', 'jquery/jquery-migrate.js',
        'jquery/jquery-ui.js',
        'noty/noty.js',
        'opentip/opentip-jquery.js',
        'fancybox/fancybox.js',
        'jstorage/jstorage.js', 'lodash/lodash.js',
        'angular/angular.js', 'bootstrap/js/bootstrap.js',
        'bootbox/bootbox.js',
        'pace/pace.js',
        'angular/angular-resource.js', 'angular/angular-route.js',
        'angular/angular-animate.js', 'angular/angular-sanitize.js',
        'angular/angular-ui-router.js',
        'datepicker/datetimepicker.js'
    ];
    jsfiles = jsfiles.map(function (p) {
        p = path + 'web/src/' + p;
        console.log(p);
        return p;
    });

    // jsfiles.push(path + '/web/requirejs/require.js');
    // jsfiles.push(path + '/web/requirejs/config.js');

    return gulp.src(jsfiles)
        .pipe(concat('libs.js'))
        .pipe(gulp.dest(path + '/web/dist/'));
});

gulp.task('libcss', ['libsjs'], function () {
    console.log('=======================Start Concat Lib CSS File========================');

    var cssfiles = [
        'jquery/jquery-ui.css',
        'noty/noty.css',
        'animate/animate.css',
        'bootstrap/css/bootstrap.css',
        'bootstrap/css/bootstrap-theme.css',
        'bootstrap/app.css',
        'bootstrap/core.css',
        'fancybox/fancybox.css',
        'bootstrap/bootstrap-reset.css',
        'pace/pace-theme-flat-top.css',
        'fontawesome/css/font-awesome.css',
        'datepicker/datetimepicker.css',
        'opentip/opentip.css',
    ];
    cssfiles = cssfiles.map(function (p) {
        p = path + 'web/src/' + p;
        return p;
    });

    return gulp.src(cssfiles)
        .pipe(concat('libs.css'))
        .pipe(gulp.dest(path + 'web/dist/css/'));
});

gulp.task('jsmin', function () {
    console.log('=======================Start Uglify JavaScript File========================');

    return gulp.src([path + '/**/*.js', '!' + path + 'web/src/**/*.js'])
        .pipe(gulp.dest(function (data) {
            console.log("Minify JS File: " + data.path);
            return data.base
        }))
        .pipe(babel({
            presets: ['minify']
        }))
        /*.pipe(babel({
            presets: ['env']
        }))
        .pipe(uglify())*/
        .pipe(rename({
            extname: '.min.js'
        }))
        .pipe(gulp.dest(function (data) {
            return data.base;
        }));
});

gulp.task('cssmin', function () {
    console.log('=======================Start Minify CSS File========================');

    return gulp.src([path + '/**/*.css', '!' + path + '/web/src/**/*.css'])
        .pipe(gulp.dest(function (data) {
            console.log("Minify CSS File: " + data.path);
            return data.base
        }))
        .pipe(cssmin())
        .pipe(rename({
            extname: '.min.css'
        }))
        .pipe(gulp.dest(function (data) {
            return data.base
        }));
});

gulp.task('copyfiles', function () {
    console.log('=======================Start Copy Font Files ========================');
    var fontfiles = [path + 'web/src/fontawesome/fonts/*'];
    gulp.src(fontfiles)
        .pipe(gulp.dest(path + 'web/dist/fonts'));

    gulp.src(path + 'web/src/datatable/image/*')
        .pipe(gulp.dest(path + 'web/dist/image'));

    return gulp.src(path + 'web/src/jquery/images/*')
        .pipe(gulp.dest(path + 'web/dist/css/images'));
});

gulp.task('default', ['libcss'], function () {
        return gulp.start('jsmin', 'cssmin', 'copyfiles');
    }
)
