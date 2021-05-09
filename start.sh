#!/bin/bash

docker build -t w2m-app . --file ./Dockerfile;
docker run -d --name super-heroes-app -p 8080:8080 w2m-app