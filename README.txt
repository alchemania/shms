1.概述
	本项目为使用Springboot+Vue混合开发的学生疫情期间的健康管理系统，
	其中前端采用Vue开发，文件位于 /shms-frontend文件夹中，后端采用Springboot开发，数据库采用Mysql（腾讯云服务托管）。
	后端代码位于/src/main/java/com/shms文件夹中，其中/admin为管理员模块的功能，/common为普通用户模块的功能

2.项目启动方法
	首先刷新Maven依赖，在IDE中启动Springboot。然后在命令行中cd shms-frontend，然后输入命令npm run dev启动前端（确保电脑上装有node 16+解释器）
	然后在浏览器中打开http：//localhost：5173即可访问项目。
	如果没有找到node_modules文件夹，可以
		(1)运行npm run preview，直接运行打包好的文件
		(2)运行npm install，安装完所有包后再运行npm run dev

3.项目后端代码概述
	后端每个文件夹都分为了至少四层，分别是controller，DAO（entity），mapper，service。utils文件夹为通用工具类，封装一些常用的工具。
	其中admin与common的详细作用在报告里有详细的说明。而对于数据持久化映射，这里采用了Mybatisplus提供的封装接口，在mapper，service层均使用了这层接口。
	通过继承mybatisplus的接口我们可以在完全不写SQL的情况下，使用QueryWrapper就可以完成所有的项目增删改查需求。

4.项目前端代码概述
	前端代码是采用Vue构建的，这不是本门课所教授的内容，所以略写。
	其中最重要的是前后端的api交互，所有api都能在 /shms-frontend/src/api/api.js中找到
	/src/components里就是主要的组件，/src/components/admin里是admin部分的组件，这两部分组件在代码上也是隔离开的
	
