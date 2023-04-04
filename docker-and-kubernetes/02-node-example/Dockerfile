FROM node:18.15.0-alpine3.16
WORKDIR /app
ADD package.json .
ADD yarn.lock .
RUN yarn install
ADD . .
CMD node index.js