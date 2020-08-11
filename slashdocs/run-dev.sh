#!/bin/bash

echo "Open http://localhost:8000 voor de docs"
docker run --rm -it -p 8000:8000 -v ${PWD}:/docs squidfunk/mkdocs-material:5.4.0
