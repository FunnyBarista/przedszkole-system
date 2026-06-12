import { http } from "./http";

// Publiczne
export function getPublicPosts() {
  return http("/api/posts");
}

// Admin
export function getAdminPosts() {
  return http("/api/admin/posts");
}

export function createPost(payload) {
  return http("/api/admin/posts", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

export function updatePost(id, payload) {
  return http(`/api/admin/posts/${id}`, {
    method: "PUT",
    body: JSON.stringify(payload),
  });
}

export function deletePost(id) {
  return http(`/api/admin/posts/${id}`, {
    method: "DELETE",
  });
}
