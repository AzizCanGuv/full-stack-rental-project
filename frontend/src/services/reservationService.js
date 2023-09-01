import customFetch from "../util/axios";
import { logoutUser } from "../features/user/userSlice";

export default class ReservationService {
  async getReservationByUserId(token,id) {
    try {
      const resp = await customFetch.get(`/reservation/getReservationsByUserId/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return resp;
    } catch (error) {
      if (error.response.status === 403) {
        // thunkAPI.dispatch(logoutUser());
      }
      throw error;
    }
  }
  async getAllReservations(token,id) {
    try {
      const resp = await customFetch.get(`/reservation/getAll`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return resp;
    } catch (error) {
      if (error.response.status === 403) {
        // thunkAPI.dispatch(logoutUser());
      }
      throw error;
    }
  }
  deleteReservation(id, token) {
    try {
      const resp = customFetch.delete(`/reservations/delete/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return resp;
    } catch (error) {
      if (error.response.status === 403) {
        // thunkAPI.dispatch(logoutUser());
      }
    }
  }
  updateReservation(id, token, body) {
    try {
      const resp = customFetch.put(`/reservations/edit/${id}`, body, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return resp;
    } catch (error) {
      if (error.response.status === 403) {
        // thunkAPI.dispatch(logoutUser());
      }
    }
  }
  createReservation(token, body) {
    try {
      const resp = customFetch.post(`/reservations/create`, body, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return resp;
    } catch (error) {
      if (error.response.status === 403) {
        // thunkAPI.dispatch(logoutUser());
      }
    }
  }
}

