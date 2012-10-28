[Davai AppTemplate]
=================

AppTemplate is a grails application template with user login based on spring security baked in.


Quick start
-----------
1. Install postgres
2. Clone the repo
3. Change application.properties to give your application a name.
4. Create a set of postgres databases for your app <appName_dev>, <appName_test>, <appName_stage>
5. grails compile
6. grails dbm-update
7. grails run-app

The app comes with two users baked in by the database migration tool:
* user@user.com
* admin@admin.com

Notes
-----
The template uses the database migration grails plugin to create tables and establish some initial user data, but the plugin is not configured to auto-update the dev profile - see directions below for dev.

The stage profile is configured to facilitate simulations of upgrading your application to a production environment.

The grails doc in the app gives some conventions for process.  Those tips depend on the herokudb project:
https://github.com/tcox5698/herokudb

Blog
----

http://davaimidwest.blogspot.com/

Authors
-------

**Tom Cox**

+ http://twitter.com/tcox5698
+ http://github.com/tcox5698

Copyright and license
---------------------

Copyright 2012 Tom Cox

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
