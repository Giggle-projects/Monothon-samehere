import styled from "@emotion/styled";
import { FC } from "react";
import { colors } from "../../design-token";

const Badge: FC = ({ children }) => {
  return <BadgeStyle>{children}</BadgeStyle>;
};

export default Badge;

const BadgeStyle = styled.div`
  background-color: ${colors.title};
  padding: 3px 8px;
  font-size: 10px;
  color: white;
  border-radius: 100px;
  font-weight: 700;
`;
