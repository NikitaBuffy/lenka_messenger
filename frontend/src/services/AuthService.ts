import axios from "axios";

class AuthService {
  async register(requestData: object) {
    try {
      const response = await axios.post('/auth/register', requestData);
      return response.data;
    } catch (error: any) {
      this.handleError(error);
    }
  }

  async login(requestData: object) {
    try {
      const response = await axios.post('/auth/login', requestData);
      return response.data;
    } catch (error: any) {
      this.handleError(error);
    }
  }

  handleError(error: any) {
    if (error.response) {
      throw error;
    } else if (error.request) {
      throw new Error("Сервер недоступен. Проверьте подключение к интернету");
    } else {
      throw new Error("Произошла неизвестная ошибка");
    }
  }
}

export default new AuthService();
