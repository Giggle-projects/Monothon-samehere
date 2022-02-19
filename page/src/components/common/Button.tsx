import styled from "@emotion/styled";
import { FC, HTMLAttributes } from "react";
import { colors } from "../../design-token";

interface Props extends HTMLAttributes<HTMLButtonElement> {
  disabled?: boolean;
}
const Button: FC<Props> = ({ children, disabled = false, ...restProps }) => {
  return (
    <ButtonStyle disabled={disabled} {...restProps}>
      {children}
    </ButtonStyle>
  );
};

export default Button;

const ButtonStyle = styled.button`
  width: 100%;
  padding: 15px 0;
  display: flex;
  align-items: center;
  border-radius: 100px;
  background-color: ${colors.icon};
  border: none;
  cursor: pointer;
  color: white;
  font-weight: 700;
  font-size: 18px;
  justify-content: center;
  max-width: 710px;

  &:disabled {
    background-color: ${colors.description};
  }
`;
