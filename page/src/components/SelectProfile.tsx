import styled from "@emotion/styled";
import { FC } from "react";
import { useNavigate } from "react-router";
import { GetGroupMembersResponse } from "../apis";
import { colors } from "../design-token";

interface Props {
  title?: string;
  groupId?: number;
  users: GetGroupMembersResponse[];
  onChange: (userId: number) => void;
  selectUserId?: number;
}
const SelectProfile: FC<Props> = ({
  title,
  users,
  onChange,
  selectUserId,
  groupId,
}) => {
  const navigate = useNavigate();
  const renderItem = (item: GetGroupMembersResponse, idx: number) => {
    return (
      <ProfileItem
        src={`http://localhost:8080${item.imageUrl}`}
        isSelect={item.id === selectUserId}
        key={idx}
        onClick={() => onChange(item.id)}
      ></ProfileItem>
    );
  };
  return (
    <SelectProfileStyle>
      <div className="title">{title}</div>
      <div className="user-profiles">
        <ProfilePlusBttuon onClick={() => navigate(`/join/${groupId}`)}>
          <svg
            width="28"
            height="28"
            viewBox="0 0 28 28"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M14 2L14 26"
              stroke="#748BAC"
              stroke-width="4"
              stroke-linecap="round"
            />
            <path
              d="M2 14H26"
              stroke="#748BAC"
              stroke-width="4"
              stroke-linecap="round"
            />
          </svg>
        </ProfilePlusBttuon>
        {users.map(renderItem)}
      </div>
    </SelectProfileStyle>
  );
};

export default SelectProfile;

const SelectProfileStyle = styled.div`
  display: flex;
  flex-direction: column;
  padding-left: 20px;
  width: 100%;
  box-sizing: border-box;
  & .title {
    text-align: center;
    font-weight: 700;
    font-size: 20px;
    color: ${colors.title};
    margin-top: 16px;
    margin-bottom: 22px;
  }
  & .user-profiles {
    display: inline-block;
    overflow: scroll;
    display: flex;
    margin-bottom: 40px;
    align-items: center;
    gap: 20px;
  }
`;

const ProfileItem = styled.img<{ isSelect: boolean }>`
  min-width: 80px;
  min-height: 80px;
  max-width: 80px;
  max-height: 80px;
  border-radius: 24px;
  cursor: pointer;
  box-sizing: border-box;
  box-shadow: 0px 6px 20px rgba(181, 203, 248, 0.2);
  background-color: white;
  transition: 0.5s;
  border: ${({ isSelect }) =>
    isSelect ? "2px solid #96b9ff" : "2px solid white"};
`;

const ProfilePlusBttuon = styled.div`
  min-width: 80px;
  min-height: 80px;

  border-radius: 24px;
  cursor: pointer;
  box-sizing: border-box;
  background-color: white;
  box-shadow: 0px 6px 20px rgba(181, 203, 248, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
`;
