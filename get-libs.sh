#!/bin/bash


# You need to run this file once after you check out from git.

# Needs to be run to install MySQL driver, necessary before sending stuff
# to production
grails install-dependency mysql:mysql-connector-java:5.1.5 --dir=lib

# Used by export plugin
grails install-dependency net.sf.opencsv:opencsv:2.0 --dir=lib

