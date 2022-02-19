import { css } from "@emotion/react";
import { colors } from "../design-token";

const globalStyles = css`
  body {
    margin: 0;
    background-color: ${colors.mainBG};
  }
  * {
    font-family: "Noto Sans KR", sans-serif;
  }
`;

export default globalStyles;
