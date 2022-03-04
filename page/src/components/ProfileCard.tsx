import styled from "@emotion/styled";
import { FC } from "react";
import { colors } from "../design-token";

interface Props {
  name: string;
  gender: string;
  job: string;
  email: string;
  imageUrl: string;
}
const ProfileCard: FC<Props> = ({ name, gender, email, job, imageUrl }) => {
  return (
    <ProfileCardStyle>
      <div className="profile-card">
        <img src={`http://localhost:8080/`+`${imageUrl}`} />
        <div className="right">
          <div className="name">
            {name} | {gender}
          </div>
          <div className="job">{job}</div>
          <div className="email">{email}</div>
        </div>
      </div>
    </ProfileCardStyle>
  );
};

export default ProfileCard;

const ProfileCardStyle = styled.div`
  padding: 0 24px;
  height: 75px;
  .profile-card {
    box-shadow: 0px 6px 20px rgba(181, 203, 248, 0.2);
    padding: 24px 20px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    border-radius: 24px;
    background-color: white;
    gap: 20px;
    & > .right {
      display: flex;
      flex-direction: column;
      gap: 8px;
      & div {
        color: ${colors.body};
        font-weight: 700;
        font-size: 13px;
      }
    }
    & img {
      min-width: 100px;
      min-height: 100px;
      width: 100px;
      height: 100px;
      object-fit: cover;
    }
  }
`;
