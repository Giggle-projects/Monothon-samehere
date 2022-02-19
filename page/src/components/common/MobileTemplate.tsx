import styled from "@emotion/styled";
import { FC } from "react";

interface Props {}
const MobileTemplate: FC<Props> = ({ children }) => {
  return (
    <MobileTemplateStyle>
      <MobileTemplateWrapper>{children}</MobileTemplateWrapper>
    </MobileTemplateStyle>
  );
};

export default MobileTemplate;

const MobileTemplateStyle = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
`;

const MobileTemplateWrapper = styled.div`
  margin: 0 auto;
  height: 100%;
  max-width: 710px;
  position: relative;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
`;
