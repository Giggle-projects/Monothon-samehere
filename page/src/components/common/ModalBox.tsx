import styled from "@emotion/styled";
import { FC } from "react";
import { colors } from "../../design-token";
import { ReactComponent as CloseIcon } from "../../static/svg/closeIcon.svg";

interface Props {
  title: string;
  closeModal?: () => void;
}
const ModalBox: FC<Props> = ({ title, children, closeModal }) => {
  return (
    <ModalBoxStyle>
      <div className="header">
        <div className="title">{title}</div>
        {closeModal && (
          <CloseIcon style={{ cursor: "pointer" }} onClick={closeModal} />
        )}
      </div>
      <div className="content">{children}</div>
    </ModalBoxStyle>
  );
};

export default ModalBox;

const ModalBoxStyle = styled.div`
  padding: 24px;
  width: 100%;
  border-radius: 20px;
  background-color: white;
  padding-bottom: 40px;
  box-sizing: border-box;
  & .header {
    padding-bottom: 20px;
    border-bottom: 1px solid ${colors.line};
    display: flex;
    align-items: center;
    justify-content: space-between;
    & .title {
      font-weight: 700;
      font-size: 20px;
      color: ${colors.title};
    }
  }
`;
