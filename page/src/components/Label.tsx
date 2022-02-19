import styled from "@emotion/styled";
import { FC } from "react";
import { colors } from "../design-token";

interface Props {
  title: string;
  content: string;
}
const Label: FC<Props> = ({ title, content}) => {
  return (
    <LabelTextStyle>
      <div className="top">
        <div className="title">{title}</div>
      </div>
      <div className="content">{content}</div>
    </LabelTextStyle>
  );
};

export default Label;

const LabelTextStyle = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 8px;
  padding: 20px 0;
  border-bottom: 1px solid #dfe7f3;
  & > .top {
    display: flex;
    align-items: center;
    column-gap: 8px;
    & .title {
      font-weight: 700;
      font-size: 14px;
    }
  }
  & > .content {
    font-weight: 400;
    font-size: 17px;
    color: ${colors.body};
  }
`;
