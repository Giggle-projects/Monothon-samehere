import styled from "@emotion/styled";
import { FC, HTMLAttributes } from "react";

interface Props extends HTMLAttributes<HTMLButtonElement> {
  icon: React.ReactNode;
}
const IconButton: FC<Props> = ({ icon, ...restProps }) => {
  return <IconButtonStyle {...restProps}>{icon}</IconButtonStyle>;
};

export default IconButton;

const IconButtonStyle = styled.button`
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  border-radius: 100px;
  border: none;
  background-color: white;
  cursor: pointer;
  justify-content: center;
  box-shadow: 0px 6px 20px rgba(181, 203, 248, 0.2);
`;
