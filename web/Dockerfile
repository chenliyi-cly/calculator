# ---------- Frontend static web service ----------
# Official nginx image
FROM nginx:alpine

# Copy the static HTML page into the nginx web root
COPY index.html /usr/share/nginx/html/index.html

# Copy the reverse-proxy configuration (forwards /calculate to the backend service)
COPY default.conf /etc/nginx/conf.d/default.conf

# Frontend serves on port 80
EXPOSE 80

# Start nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
