import styled from "@emotion/styled";
import { useEffect, useState } from "react";
import IconButton from "../components/common/IconButton";
import MobileTemplate from "../components/common/MobileTemplate";
import FlotingModal from "../components/FlotingModal";
import UserProfile from "../components/UserProfile";
import { ReactComponent as EditIcon } from "../static/svg/editIcon.svg";
import { ReactComponent as PlusIcon } from "../static/svg/plusIcon.svg";
import OutsideClickHandler from "react-outside-click-handler";
import Button from "../components/common/Button";
import Modal from "../components/common/Modal";
import { useNavigate, useParams } from "react-router";
import { getCard, GetCardResponse } from "../apis";
import { colors } from "../design-token";
import { copyToClibboard } from "../components/GroupEmpty";

const Profile = () => {
  const { userId } = useParams();
  const navigate = useNavigate();
  const [data, setData] = useState<GetCardResponse>();
  const [isOpen, setIsOpen] = useState<"floating" | "group" | "unset">("unset");

  return (
    <MobileTemplate>
      {isOpen === "floating" && (
        <OutsideClickHandler onOutsideClick={() => setIsOpen("unset")}>
          <FlotingModal>
            <Button onClick={() => navigate("/create")}>
              새 프로필 만들기
            </Button>
            <Button onClick={() => setIsOpen("group")}>그룹 만들기</Button>
          </FlotingModal>
        </OutsideClickHandler>
      )}
      {isOpen === "group" && (
        <Modal
          onSubmit={() => ""}
          closeModal={() => setIsOpen("unset")}
        ></Modal>
      )}
      <ButtonBox className="button-box">
        <IconButton
          icon={<EditIcon onClick={() => navigate(`/edit/${userId}`)} />}
        />
        <div className="right">
          <div
            className="copy-button"
            onClick={() => {
              copyToClibboard(`http://localhost:1080/profile/${userId}`);
              alert("클립보드에 복사되었습니다.");
            }}
          >
            ID: {userId}
            <svg
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <rect width="24" height="24" rx="12" fill="#748BAC" />
              <path
                d="M9.54547 9.00002L12 6.54547L14.4546 9.00002"
                stroke="white"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M12 12.9273V7.52731"
                stroke="white"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M16.9091 11.9455V14.3637C16.9091 15.4682 16.0137 16.3637 14.9091 16.3637H9.09091C7.98634 16.3637 7.09091 15.4682 7.09091 14.3637V11.9455"
                stroke="white"
                stroke-width="2"
                stroke-linecap="round"
              />
            </svg>
          </div>
          <IconButton
            onClick={() => setIsOpen("floating")}
            icon={<PlusIcon />}
          />
        </div>
      </ButtonBox>
      {userId && <UserProfile userId={parseInt(userId)}></UserProfile>}
    </MobileTemplate>
  );
};

export default Profile;

const ButtonBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  margin-bottom: 12px;
  margin-top: 10px;
  & .right {
    display: flex;
    align-items: center;
  }
  & .copy-button {
    display: flex;
    background-color: white;
    box-shadow: 0px 6px 20px rgba(181, 203, 248, 0.2);
    padding: 4px 0;
    width: 104px;
    color: ${colors.title};
    font-weight: 700;
    font-size: 14px;
    align-items: center;
    box-sizing: border-box;
    padding-left: 10px;
    padding-right: 5px;
    border-radius: 100px;
    margin-right: 8px;
    cursor: pointer;
    justify-content: space-between;
  }
`;
