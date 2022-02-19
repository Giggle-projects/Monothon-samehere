import styled from "@emotion/styled";
import ProfileCard from "./ProfileCard";
import Label from "./Label";
import {FC, useEffect, useState} from "react";
import {getCard, GetCardResponse} from "../apis";

interface Props {
  userId: number;
}

const UserProfile: FC<Props> = ({ userId }) => {
  const [data, setData] = useState<GetCardResponse>();
  useEffect(() => {
    getCard(userId).then((res) => setData(res.data));
  }, [userId]);
  return (
    <UserProfileStyle>
      <div className="user-profile">
        {data && (
          <ProfileCard
            name={data.name}
            gender={data.gender}
            email={data.email}
            imageUrl={data.imageUrl}
            job={data.profession}
          />
        )}
        <LabelWrapper>
          {data?.cardItems.map(({...item}, idx) => (
            <Label
              key={idx}
              title={item.itemName}
              content={item.value}
            />
          ))}
        </LabelWrapper>
      </div>
    </UserProfileStyle>
  );
};

export default UserProfile;

const UserProfileStyle = styled.div`
  padding-top: 75px;

  flex: 1;
  & .profile-card {
    top: 30px;
    transform: translateY(-75px);
  }
  & .user-profile {
    padding-bottom: 30px;
    background-color: white;
    height: 100%;
  }
`;

const LabelWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 8px;
  padding: 0 28px;
  & div:nth-last-child(1) {
    border-bottom: none;
  }
`;
