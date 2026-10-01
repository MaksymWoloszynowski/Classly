FROM node:20-alpine AS deps

WORKDIR /opt/app
COPY package*.json ./
RUN npm install

FROM node:20-alpine AS build

WORKDIR /opt/app
COPY --from=deps /opt/app/node_modules ./node_modules
COPY . .

ARG VITE_API_BASE_URL=/
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL

RUN npm run build

FROM nginxinc/nginx-unprivileged:stable-alpine

COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /opt/app/dist /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]