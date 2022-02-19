import styled from "@emotion/styled";
import { FC, HTMLAttributes } from "react";
import { colors } from "../../design-token";

interface Props extends HTMLAttributes<HTMLInputElement> {
  title: string;
  name: string;
  value?: string;
  readOnly?: boolean;
}
const Input: FC<Props> = ({ title, readOnly, ...restProps }) => {
  return (
    <InputStyle>
      <div className="title">{title}</div>
      <input maxLength={50} readOnly={readOnly} type="text" {...restProps} />
    </InputStyle>
  );
};

export default Input;

const InputStyle = styled.div`
  display: flex;
  flex-direction: column;

  & .title {
    color: ${colors.title};
    font-size: 16px;
    font-weight: 700;
    margin-bottom: 10px;
  }
  & input {
    background-color: white;
    border-radius: 8px;
    padding: 12px 20px;
    border: 1px solid ${colors.line};
  }
`;
