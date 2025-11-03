FROM node:22 as builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine as final
WORKDIR /app
COPY --from=builder /app/build /var/www/build
CMD ["nginx", "-g", "daemon off;"]