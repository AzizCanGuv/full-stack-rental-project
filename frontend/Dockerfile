FROM node:14

WORKDIR /app

COPY package.json package-lock.json* yarn.lock* ./

RUN npm install

COPY . .

RUN npm run build

EXPOSE 3000

CMD ["npm", "start"]
