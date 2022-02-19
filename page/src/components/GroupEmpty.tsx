import styled from "@emotion/styled";
import { FC } from "react";
import { colors } from "../design-token";
import Input from "./common/Input";

export const copyToClibboard = (text: string) => {
  const textArea = document.createElement("textarea");
  textArea.value = text;
  document.body.appendChild(textArea);
  textArea.select();
  textArea.setSelectionRange(0, 9999);
  document.execCommand("copy");
  document.body.removeChild(textArea);
};
interface Props {
  groupId: number;
}
const GroupEmpty: FC<Props> = ({ groupId }) => {
  return (
    <GroupEmptyStyle>
      <div className="user-profile">
        <div className="profile-card-wrapper">
          <div className="profile-card"></div>
        </div>
        <EmptyWrapper>
          <div className="title">그룹원이 들어오길 기다리는 중이에요 </div>
          <div className="description">
            <b>그룹 링크를 다시 공유</b>하고 싶으신가요? <br />
            <b>아래 버튼</b>을 누르시면 <br />
            <b>그룹 링크를</b> 복사할 수 있어요 !
          </div>
          <div className="copy-wrapper">
            <Input
              title=""
              name=""
              readOnly
              onClick={() => {
                copyToClibboard(`http://localhost:1080/group/${groupId}`);
                alert("클립보드에 복사되었습니다.");
              }}
              value={`http://localhost:1080/group/${groupId}`}
            ></Input>
          </div>
        </EmptyWrapper>
      </div>
    </GroupEmptyStyle>
  );
};

export default GroupEmpty;

const GroupEmptyStyle = styled.div`
  padding-top: 75px;
  flex: 1;
  & .profile-card-wrapper {
    padding: 24px 20px;
    height: 0;
  }
  & .profile-card {
    height: 148px;
    top: 30px;
    transform: translateY(-75px);
    box-shadow: 0px 6px 20px rgba(181, 203, 248, 0.2);

    border-radius: 24px;
    background-color: white;
  }
  & .user-profile {
    background-color: white;
    height: 100%;
  }
`;

const EmptyWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 8px;
  text-align: center;
  padding: 0 28px;
  & .copy-wrapper {
    padding: 0 4px;
    margin-top: 20px;
    & input {
      text-align: center;
      color: ${colors.title};
      font-weight: 700;
      font-size: 16px;
    }
  }
  & > .title {
    color: ${colors.title};
    font-weight: 700;
    font-size: 16px;
    margin-top: 80px;
  }
  & > .description {
    color: ${colors.body};
    font-weight: 500;
    font-size: 14px;
    margin-top: 40px;
  }
`;
