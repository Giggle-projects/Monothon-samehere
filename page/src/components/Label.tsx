import styled from "@emotion/styled";
import { FC } from "react";
import { CardItems } from "../apis";
import { colors } from "../design-token";
import Badge from "./common/Badge";

interface Props {
  title: string;
  content: string;
  sameHere: boolean;
}
const Label: FC<Props> = ({ title, content, sameHere = false }) => {
  return (
    <LabelTextStyle>
      <div className="top">
        <div className="title">{title}</div>
        {sameHere && <Badge>Same Here 👋</Badge>}
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
