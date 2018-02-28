package com.Tirax.plasma;

import com.Tirax.plasma.Options.Auto.Acne.AcneHighOption;
import com.Tirax.plasma.Options.Auto.Acne.AcneLowOption;
import com.Tirax.plasma.Options.Auto.Acne.AcneMediumOption;
import com.Tirax.plasma.Options.Auto.Blepharo.BlepharoHighOption;
import com.Tirax.plasma.Options.Auto.Blepharo.BlepharoLowOption;
import com.Tirax.plasma.Options.Auto.Blepharo.BlepharoMediumOption;
import com.Tirax.plasma.Options.Auto.Mole.MoleHighOption;
import com.Tirax.plasma.Options.Auto.Mole.MoleLowOption;
import com.Tirax.plasma.Options.Auto.Mole.MoleMediumOption;
import com.Tirax.plasma.Options.Auto.Scar.ScarHighOption;
import com.Tirax.plasma.Options.Auto.Scar.ScarLowOption;
import com.Tirax.plasma.Options.Auto.Scar.ScarMediumOption;
import com.Tirax.plasma.Options.ManualOption;
import com.Tirax.plasma.Storage.Pages;

/**
 * Created by a.irani on 11/1/2016.
 */
public class Manager {
    public static Mode getType() {

        if (Pages.auto_manual == Pages.MANUAL) {

            return new ManualOption();

        } else {
            if (Pages.auto_type == Pages.ACNE) {

                if (Pages.step == Pages.HIGH) {
                    return new AcneHighOption();
                } else if (Pages.step == Pages.MEDIUM) {
                    return new AcneMediumOption();
                } else if (Pages.step == Pages.LOW) {
                    return new AcneLowOption();
                }

            }else if (Pages.auto_type == Pages.BLEPHARO) {

                if (Pages.step == Pages.HIGH) {
                    return new BlepharoHighOption();
                } else if (Pages.step == Pages.MEDIUM) {
                    return new BlepharoMediumOption();
                } else if (Pages.step == Pages.LOW) {
                    return new BlepharoLowOption();
                }

            }else if (Pages.auto_type == Pages.SCAR) {

                if (Pages.step == Pages.HIGH) {
                    return new ScarHighOption();
                } else if (Pages.step == Pages.MEDIUM) {
                    return new ScarMediumOption();
                } else if (Pages.step == Pages.LOW) {
                    return new ScarLowOption();
                }

            }else if (Pages.auto_type == Pages.MOLE) {

                if (Pages.step == Pages.HIGH) {
                    return new MoleHighOption();
                } else if (Pages.step == Pages.MEDIUM) {
                    return new MoleMediumOption();
                } else if (Pages.step == Pages.LOW) {
                    return new MoleLowOption();
                }

            }
            return null;
        }

    }




};

