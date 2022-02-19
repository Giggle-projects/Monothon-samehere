import axios from "axios";
import { getCookie } from "../pages/Join";

const request = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true,
});

interface CreateGroupPayload {
  name: string;
  password: string;
}

interface CreateGroupResponse {
  id: number;
  name: string;
}

export const createGroup = (data: CreateGroupPayload) =>
  request.post<CreateGroupResponse>("/groups", data);

export interface CardItems {
  itemName: string;
  value: string;
  sameHere: boolean;
}
export interface EditCardItems {
  itemId: number;
  value: string;
}
export interface GetCardResponse {
  id: number;
  name: string;
  gender: string;
  email: string;
  imageUrl: string;
  profession: string;
  groups: GetGroupResponse[];
  cardItems: CardItems[];
}

interface CreateCardResponse {
  id: number;
}

export interface EditCardPayload {
  name: string;
  gender: string;
  password: string;
  email: string;
  profession: string;
  cardItems?: EditCardItems[];
}

export const createCard = (data: FormData) =>
  request.post<CreateCardResponse>("/cards", data, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });

export const editCard = (cardId: number, data: EditCardPayload) =>
  request.put(`/cards/${cardId}`, data);

export interface GetGroupMembersResponse {
  id: number;
  name: string;
  imageUrl: string;
  gender: string;
}
export const getGroupMembers = (groupId: number) =>
  request.get<GetGroupMembersResponse[]>(`/cards/groups/${groupId}`);

export interface GetGroupResponse {
  id: number;
  name: string;
}
export const getGroup = (groupId: number) =>
  request.get<GetGroupResponse>(`/groups/${groupId}`);
export const getCard = (userId: number) =>
  request.get<GetCardResponse>(
    `/cards/${userId}${
      window.localStorage.myId ? `?myId=${window.localStorage.myId}` : ""
    }`
  );

export const joinGroup = (cardId: number, groupId: number) =>
  request.post(`/cards/${cardId}/groups/${groupId}`);
