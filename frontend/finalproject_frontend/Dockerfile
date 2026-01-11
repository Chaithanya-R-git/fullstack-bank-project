# ---------- Stage 1: Build ----------
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npx ng build finalproject_frontend --configuration production

# ---------- Stage 2: Nginx ----------
FROM nginx:alpine

# Remove default nginx static assets
RUN rm -rf /usr/share/nginx/html/*

# THE FIX: Added /browser to the end of the path
COPY --from=build /app/dist/finalproject_frontend/browser /usr/share/nginx/html

# This line ensures Angular routing works (prevents 404 on refresh)
RUN sed -i 's/index  index.html index.htm;/index  index.html index.htm; try_files $uri $uri\/ \/index.html;/g' /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]