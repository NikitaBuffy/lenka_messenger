import axios from "axios";

class AuthService {
  async register(requestData) {
    try {
      const response = await axios.post('/auth/register', requestData);
      return response.data;
    } catch (error) {
      this.handleError(error);
    }
  }

  async login(requestData) {
    try {
      const response = await axios.post('/auth/login', requestData);
      return response.data;
    } catch (error) {
      this.handleError(error);
    }
  }

  handleError(error) {
    if (error.response) {
      throw new Error(error.response.data?.message || "Произошла ошибка на сервере");
    } else if (error.request) {
      throw new Error("Сервер недоступен. Проверьте подключение к интернету.");
    } else {
      throw new Error("Произошла неизвестная ошибка");
    }
  }
}

export default new AuthService();
